package no.runsafe.peeveepeearena;

import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.PlayerRatingRepository;

public class RatingHandler
{
	public RatingHandler(PlayerRatingRepository repository)
	{
		this.repository = repository;
	}

	public int getRating(RunsafePlayer player)
	{
		return this.repository.getRating(player);
	}

	private PlayerRatingRepository repository;
}
