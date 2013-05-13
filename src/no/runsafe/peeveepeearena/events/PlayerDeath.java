package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.event.player.IPlayerDeathEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.event.player.RunsafePlayerDeathEvent;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.item.meta.RunsafeItemMeta;
import no.runsafe.framework.server.item.meta.RunsafeSkullMeta;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.mailbox.MailSender;
import no.runsafe.peeveepeearena.RatingHandler;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDeath implements IConfigurationChanged, IPlayerDeathEvent
{
	public PlayerDeath(PlayerScoresRepository playerScoresRepository, MailSender mailSender, IOutput output, RatingHandler ratingHandler)
	{
		this.playerScoresRepository = playerScoresRepository;
		this.mailSender = mailSender;
		this.output = output;
		this.ratingHandler = ratingHandler;

		this.currency = new RunsafeItemStack(Material.BONE.getId(), 1);
		RunsafeItemMeta meta = this.currency.getItemMeta();
		meta.setDisplayName("PvP Kill Token");
		meta.addLore("Used to buy items for the PvP arena.");
		this.currency.setItemMeta(meta);
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
			this.kills.remove(killed.getName());
			List<Integer> ratings = this.ratingHandler.getNewRating(killer, killed);

			int winnerRatingChange = ratings.get(0);
			int looserRatingChange = ratings.get(1);

			killer.sendColouredMessage(
				String.format(
					"&fYou &agained&f %s rating for killing %s&f.",
					(winnerRatingChange == 0 ? "no" : winnerRatingChange),
					killed.getPrettyName()
				)
			);

			killed.sendColouredMessage(
				String.format(
						"&fYou &clost&f %s rating from being killed by %s&f.",
						(looserRatingChange == 0 ? "no" : looserRatingChange),
						killer.getPrettyName()
				)
			);

			int pointsGain = winnerRatingChange * this.pointsPerRating;
			this.currency.setAmount(pointsGain);
			killer.getInventory().addItems(this.currency.clone());
			killer.sendColouredMessage(String.format("&fYou gain &a%s&f PvP tokens.", pointsGain));

			this.playerScoresRepository.incrementDeaths(killed);
			this.playerScoresRepository.incrementKills(killer);

			if ((Math.random() * 100) + 1 <= this.headDropChance)
			{
				if (this.mailSender.hasFreeMailboxSpace(killer))
				{
					RunsafeInventory newPackage = RunsafeServer.Instance.createInventory(null, 54);
					RunsafeItemStack head = new RunsafeItemStack(Material.SKULL_ITEM.getId(), 1, (short) 3);
					RunsafeSkullMeta meta = (RunsafeSkullMeta) head.getItemMeta();
					meta.setOwner(killed.getName());
					head.setItemMeta(meta);
					newPackage.addItems(head);
					this.mailSender.sendMail(killer, "Kjorn the arena janitor", newPackage);
					this.output.broadcastColoured(String.format("&f%s&f gained the head of %s&f from PvP.", killer.getPrettyName(), killed.getPrettyName()));
				}
			}
		}
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorldName = configuration.getConfigValueAsString("pvpWorld");
		this.headDropChance = configuration.getConfigValueAsInt("headDropChance");
		this.pointsPerRating = configuration.getConfigValueAsInt("pointsPerRating");
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
			RunsafeServer.Instance.broadcastMessage(String.format(broadcast, player.getPrettyName()));
	}

	private RunsafeItemStack currency;
	private PlayerScoresRepository playerScoresRepository;
	private String pvpWorldName;
	private MailSender mailSender;
	private HashMap<String, Integer> kills = new HashMap<String, Integer>();
	private IOutput output;
	private int headDropChance;
	private int pointsPerRating;
	private RatingHandler ratingHandler;
}
