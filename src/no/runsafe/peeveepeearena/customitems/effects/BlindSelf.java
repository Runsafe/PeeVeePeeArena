package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.server.player.RunsafePlayer;

public class BlindSelf implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		Buff.Combat.Blindness.duration(10).amplification(60).applyTo(player);
	}
}
