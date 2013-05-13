package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;

import java.util.HashMap;

public class GetPoints extends PlayerCommand
{
	public GetPoints(PlayerScoresRepository playerScoresRepository)
	{
		super("points", "Returns the amount of points you have in PvP", "runsafe.peeveepee.points.get");
		this.playerScoresRepository = playerScoresRepository;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		return String.format("&fYou currently have &a%s&f PvP points to spend.", this.playerScoresRepository.getPoints(executor));
	}

	private PlayerScoresRepository playerScoresRepository;
}
