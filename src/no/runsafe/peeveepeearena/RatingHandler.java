package no.runsafe.peeveepeearena;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;

import java.util.ArrayList;
import java.util.List;

public class RatingHandler implements IConfigurationChanged
{
	public RatingHandler(PlayerScoresRepository repository)
	{
		this.repository = repository;
	}

	public int getRating(IPlayer player)
	{
		return this.repository.getRating(player);
	}

	private double getExpectedRating(int playerRating, int againstPlayerRating)
	{
		return 1 / (1 + Math.pow(10, (double) (againstPlayerRating - playerRating) / 400));
	}

	public List<Integer> getNewRating(IPlayer winner, IPlayer looser)
	{
		List<Integer> ratings = new ArrayList<>();
		int winnerRating = this.getRating(winner);
		int looserRating = this.getRating(looser);

		int newWinnerRating = (int) Math.round(winnerRating + this.kFactor * (1 - this.getExpectedRating(winnerRating, looserRating)));
		int newLooserRating = (int) Math.round(looserRating + this.kFactor * (0 - this.getExpectedRating(looserRating, winnerRating)));

		ratings.add(0, Math.abs(newWinnerRating - winnerRating));
		ratings.add(1, Math.abs(newLooserRating - looserRating));

		this.repository.updateRating(winner, newWinnerRating);
		this.repository.updateRating(looser, newLooserRating);

		return ratings;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.kFactor = configuration.getConfigValueAsInt("ratingKFactor");
	}

	private final PlayerScoresRepository repository;
	private int kFactor;
}
