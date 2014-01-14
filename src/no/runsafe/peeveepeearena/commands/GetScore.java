package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;

public class GetScore extends PlayerCommand
{
	public GetScore(PlayerScoresRepository scoresRepository)
	{
		super("score", "Returns your PvP kill/death count", "runsafe.peeveepee.score.get");
		this.scoresRepository = scoresRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		IRow scores = this.scoresRepository.getScores(executor);
		int kills = (scores.Integer("kills") != null ? scores.Integer("kills") : 0);
		int deaths = (scores.Integer("deaths") != null ? scores.Integer("deaths") : 0);

		return String.format("&fYou have &a%s&f kills and &c%s&f deaths.", kills, deaths);
	}

	private final PlayerScoresRepository scoresRepository;
}
