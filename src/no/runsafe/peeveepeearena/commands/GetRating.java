package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.peeveepeearena.RatingHandler;

import java.util.Map;

public class GetRating extends PlayerCommand
{
	public GetRating(RatingHandler ratingHandler)
	{
		super("rating", "Gets your current PvP rating", "runsafe.peeveepee.rating.get");
		this.ratingHandler = ratingHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		int rating = this.ratingHandler.getRating(executor);
		return String.format("&fYour PvP rating is currently %s%s&f.", (rating < 0 ? "&a" : "&c"), rating);
	}

	private final RatingHandler ratingHandler;
}
