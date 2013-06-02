package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.server.player.RunsafePlayer;

public class SpeedBoost implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		Buff.Utility.Movement.IncreaseSpeed.duration(15).amplification(3).applyTo(player);
	}
}
