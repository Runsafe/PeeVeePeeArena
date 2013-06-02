package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.server.player.RunsafePlayer;

public class StrengthBoost implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		Buff.Combat.Damage.Increase.duration(20).amplification(1).applyTo(player);
	}
}
