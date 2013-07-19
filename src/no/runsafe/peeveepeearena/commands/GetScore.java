package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;

import java.util.Map;

public class GetScore extends PlayerCommand
{
	public GetScore(PlayerScoresRepository scoresRepository)
	{
		super("score", "Returns your PvP kill/death count", "runsafe.peeveepee.score.get");
		this.scoresRepository = scoresRepository;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		IRow scores = this.scoresRepository.getScores(executor);
		int kills = (scores.Integer("kills") != null ? scores.Integer("kills") : 0);
		int deaths = (scores.Integer("deaths") != null ? scores.Integer("deaths") : 0);

		return String.format("&fYou have &a%s&f kills and &c%s&f deaths.", kills, deaths);
	}

	private final PlayerScoresRepository scoresRepository;
}
