package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class SpeedBoost implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		Buff.Utility.Movement.IncreaseSpeed.amplification(3).duration(15).applyTo(player);
	}
}
