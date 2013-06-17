package no.runsafe.peeveepeearena.customevents;

import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class WickedSickSpreeEvent extends RunsafeCustomEvent
{
	public WickedSickSpreeEvent(RunsafePlayer player)
	{
		super(player, "peeveepee.killspree.wickedsick");
	}

	@Override
	public Object getData()
	{
		return null;
	}
}
