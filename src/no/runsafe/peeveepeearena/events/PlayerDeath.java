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
import org.bukkit.Material;

import java.util.ArrayList;

public class PlayerDeath implements IConfigurationChanged, IPlayerDeathEvent
{
	public PlayerDeath(MailHandler mailHandler)
	{
		this.mailHandler = mailHandler;
	}

	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		RunsafePlayer killed = event.getEntity();
		if (killed.getWorld().getName().equals(pvpWorldName))
		{
			event.setDrops(new ArrayList<RunsafeItemStack>());

			if (Math.random() * 100 == 0)
			{
				RunsafePlayer killer = event.getEntity().getKiller();
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

	private String pvpWorldName;
	private MailHandler mailHandler;
}
