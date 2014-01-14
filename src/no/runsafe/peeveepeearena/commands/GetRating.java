package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.RatingHandler;

public class GetRating extends PlayerCommand
{
	public GetRating(RatingHandler ratingHandler)
	{
		super("rating", "Gets your current PvP rating", "runsafe.peeveepee.rating.get");
		this.ratingHandler = ratingHandler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{

		int rating = this.ratingHandler.getRating(executor);
		return String.format("&fYour PvP rating is currently %s%s&f.", (rating < 0 ? "&a" : "&c"), rating);
	}

	private final RatingHandler ratingHandler;
}
