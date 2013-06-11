package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class BlindSelf implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		Buff.Combat.Blindness.amplification(20).duration(10).applyTo(player);
	}
}
