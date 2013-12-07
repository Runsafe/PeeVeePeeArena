package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;

import java.util.Map;

public class GetPoints extends PlayerCommand
{
	public GetPoints(PlayerScoresRepository playerScoresRepository)
	{
		super("points", "Returns the amount of points you have in PvP", "runsafe.peeveepee.points.get");
		this.playerScoresRepository = playerScoresRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, Map<String, String> parameters)
	{
		return String.format("&fYou currently have &a%s&f PvP points to spend.", this.playerScoresRepository.getPoints(executor));
	}

	private final PlayerScoresRepository playerScoresRepository;
}
