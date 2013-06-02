package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.server.player.RunsafePlayer;

public class PoisonSelf implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		Buff.Combat.Damage.Poison.amplification(1).duration(10).applyTo(player);
	}
}
