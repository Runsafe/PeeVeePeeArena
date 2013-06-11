package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class StrengthBoost implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		Buff.Combat.Damage.Increase.amplification(1).duration(20).applyTo(player);
	}
}
