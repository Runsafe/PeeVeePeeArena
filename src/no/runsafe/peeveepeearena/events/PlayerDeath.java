package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.event.player.IPlayerDeathEvent;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.event.player.RunsafePlayerDeathEvent;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.item.meta.RunsafeSkullMeta;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.mailbox.MailHandler;
import no.runsafe.peeveepeearena.PvPArenaEngine;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDeath implements IConfigurationChanged, IPlayerDeathEvent
{
	public PlayerDeath(MailHandler mailHandler, PvPArenaEngine engine)
	{
		this.mailHandler = mailHandler;
		this.engine = engine;
	}

	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		RunsafePlayer killed = event.getEntity();
		if (killed.getWorld().getName().equals(pvpWorldName))
		{
			event.setDrops(new ArrayList<RunsafeItemStack>());
			RunsafePlayer killer = event.getEntity().getKiller();
			this.killSpreeCheck(killer);

			killed.sendColouredMessage(String.format("&fYou lost no rating from being killed by %s&f.", killer.getPrettyName()));
			killer.sendColouredMessage(String.format("&fYou gained no rating for killing %s&f.", killed.getPrettyName()));

			if (Math.random() * 100 == 0)
			{
				if (this.mailHandler.hasFreeMailboxSpace(killer))
				{
					RunsafeInventory newPackage = RunsafeServer.Instance.createInventory(null, 54);
					RunsafeItemStack head = new RunsafeItemStack(Material.SKULL_ITEM.getId(), 1, (short) 3);
					RunsafeSkullMeta meta = (RunsafeSkullMeta) head.getItemMeta();
					meta.setOwner(killed.getName());
					head.setItemMeta(meta);
					newPackage.addItems(head);
					this.mailHandler.sendMail(killer, "Kjorn the PvP Janitor", newPackage);
				}
			}
		}
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorldName = configuration.getConfigValueAsString("pvpWorld");
	}

	private void killSpreeCheck(RunsafePlayer player)
	{
		String playerName = player.getName();
		int kills = (this.kills.containsKey(playerName) ? this.kills.get(playerName) + 1 : 1);
		this.kills.put(playerName, kills);

		String broadcast = null;
		switch (kills)
		{
			case 5: broadcast = "%s&e is on a killing spree."; break;
			case 10: broadcast = "%s&e is on a rampage."; break;
			case 15: broadcast = "%s&e is dominating."; break;
			case 20: broadcast = "%s&e is unstoppable."; break;
			case 25: broadcast = "%s&e is godlike!"; break;
			case 30: broadcast = "%s&e is wicked sick!"; break;
		}

		if (broadcast != null)
			this.engine.broadcastMessage(String.format(broadcast, player.getPrettyName()));
	}

	private String pvpWorldName;
	private MailHandler mailHandler;
	private PvPArenaEngine engine;
	private HashMap<String, Integer> kills = new HashMap<String, Integer>();
}
