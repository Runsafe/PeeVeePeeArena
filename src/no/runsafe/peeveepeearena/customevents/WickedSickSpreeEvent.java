package no.runsafe.peeveepeearena.customevents;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;

public class WickedSickSpreeEvent extends RunsafeCustomEvent
{
	public WickedSickSpreeEvent(IPlayer player)
	{
		super(player, "peeveepee.killspree.wickedsick");
	}

	@Override
	public Object getData()
	{
		return null;
	}
}
