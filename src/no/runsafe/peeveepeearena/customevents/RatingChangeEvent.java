package no.runsafe.peeveepeearena.customevents;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;

public class RatingChangeEvent extends RunsafeCustomEvent
{
	public RatingChangeEvent(IPlayer player, Integer rating)
	{
		super(player, "peeveepee.rating.change");
		this.rating = rating;
	}

	@Override
	public Integer getData()
	{
		return this.rating;
	}

	private final Integer rating;
}
