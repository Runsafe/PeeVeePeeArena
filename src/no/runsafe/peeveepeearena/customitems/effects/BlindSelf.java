package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;

public class BlindSelf implements ICustomItemEffect
{
	@Override
	public void performEffect(IPlayer player)
	{
		Buff.Combat.Blindness.amplification(20).duration(10).applyTo(player);
	}
}
