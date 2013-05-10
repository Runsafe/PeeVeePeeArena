package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.PlayerRatingRepository;

import java.util.HashMap;

public class GetRating extends PlayerCommand
{
	public GetRating(PlayerRatingRepository playerRatingRepository)
	{
		super("rating", "Gets your current PvP rating", "runsafe.peeveepee.rating.get");
		this.playerRatingRepository = playerRatingRepository;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		return String.format("&3Your PvP rating is currently %s.", this.playerRatingRepository.getRating(executor));
	}

	private PlayerRatingRepository playerRatingRepository;
}
