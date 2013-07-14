package no.runsafe.peeveepeearena.customevents;

import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class RatingChangeEvent extends RunsafeCustomEvent
{
	public RatingChangeEvent(RunsafePlayer player, Integer rating)
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
