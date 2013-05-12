package no.runsafe.peeveepeearena;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.PlayerRatingRepository;

import java.util.ArrayList;
import java.util.List;

public class RatingHandler implements IConfigurationChanged
{
	public RatingHandler(PlayerRatingRepository repository, IOutput output)
	{
		this.repository = repository;
		this.output = output;
	}

	public int getRating(RunsafePlayer player)
	{
		return this.repository.getRating(player);
	}

	private double getExpectedRating(int playerRating, int againstPlayerRating)
	{
		double r = 1 / (1 + Math.pow(10, (againstPlayerRating - playerRating) / 400));
		this.output.broadcastColoured("R: " + r + " E: 0.5@1500/1500");
		return r;
	}

	public List<Integer> getNewRating(RunsafePlayer winner, RunsafePlayer looser)
	{
		List<Integer> ratings = new ArrayList<Integer>();
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

	private PlayerRatingRepository repository;
	private int kFactor;
	private IOutput output;
}
