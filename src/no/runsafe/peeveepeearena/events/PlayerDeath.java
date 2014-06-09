package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.player.IPlayerDeathEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerDeathEvent;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.item.meta.RunsafeSkull;
import no.runsafe.mailbox.MailSender;
import no.runsafe.peeveepeearena.RatingHandler;
import no.runsafe.peeveepeearena.customevents.WickedSickSpreeEvent;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDeath implements IConfigurationChanged, IPlayerDeathEvent
{
	public PlayerDeath(PlayerScoresRepository playerScoresRepository, MailSender mailSender, RatingHandler ratingHandler, IServer server)
	{
		this.playerScoresRepository = playerScoresRepository;
		this.mailSender = mailSender;
		this.ratingHandler = ratingHandler;
		this.server = server;
	}

	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		IPlayer killed = event.getEntity();
		if (killed.getWorldName().equals(pvpWorldName))
		{
			event.setDrops(new ArrayList<RunsafeMeta>());

			IPlayer killer = event.getEntity().getKiller();
			if (killer != null && !killer.getName().equals(killed.getName()))
			{
				this.killSpreeCheck(killer, killed);
				List<Integer> ratings = this.ratingHandler.getNewRating(killer, killed);

				int winnerRatingChange = ratings.get(0);
				int looserRatingChange = ratings.get(1);

				killer.sendColouredMessage(
					"&7&oYou gained %s rating for killing %s.",
					(winnerRatingChange == 0 ? "no" : winnerRatingChange),
					killed.getName()
				);

				killed.sendColouredMessage(
					"&7&oYou lost %s rating from being killed by %s.",
					(looserRatingChange == 0 ? "no" : looserRatingChange),
					killer.getName()
				);

				int pointsGain = winnerRatingChange * this.pointsPerRating;
				killer.sendColouredMessage(String.format("&7&oYou gain %s PvP points.", pointsGain));
				this.playerScoresRepository.updatePoints(killer, pointsGain);

				this.playerScoresRepository.incrementDeaths(killed);
				this.playerScoresRepository.incrementKills(killer);

				if ((Math.random() * 100) + 1 <= this.headDropChance)
				{
					if (this.mailSender.hasFreeMailboxSpace(killer))
					{
						RunsafeInventory newPackage = server.createInventory(null, 54);
						RunsafeSkull head = (RunsafeSkull) Item.Decoration.Head.Human.getItem();
						head.setPlayer(killed);
						head.setAmount(1);
						newPackage.addItems(head);
						this.mailSender.sendMail(killer, "Kjorn the Arena Janitor", newPackage);
					}
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

	private void killSpreeCheck(IPlayer killer, IPlayer killed)
	{
		String playerName = killer.getName();
		int killCount = (kills.containsKey(playerName) ? kills.get(playerName) + 1 : 1);
		this.kills.put(playerName, killCount);

		String broadcast = null;
		switch (killCount)
		{
			case 5:
				broadcast = "%s&e is on a killing spree.";
				break;
			case 10:
				broadcast = "%s&e is on a rampage.";
				break;
			case 15:
				broadcast = "%s&e is dominating.";
				break;
			case 20:
				broadcast = "%s&e is unstoppable.";
				break;
			case 25:
				broadcast = "%s&e is godlike!";
				break;
			case 30:
				broadcast = "%s&e is wicked sick!";
				new WickedSickSpreeEvent(killer).Fire();
				break;
		}

		if (broadcast != null)
			server.broadcastMessage(String.format(broadcast, killer.getPrettyName()));

		String killedName = killed.getName();
		if (kills.containsKey(killedName))
		{
			if (kills.get(killedName) > 5)
				server.broadcastMessage(killed.getPrettyName() + "&e's killing spree was ended by " + killer.getPrettyName() + "&e.");

			kills.remove(killedName);
		}
	}

	private final PlayerScoresRepository playerScoresRepository;
	private String pvpWorldName;
	private final MailSender mailSender;
	private final HashMap<String, Integer> kills = new HashMap<String, Integer>();
	private int headDropChance;
	private int pointsPerRating;
	private final RatingHandler ratingHandler;
	private final IServer server;
}
