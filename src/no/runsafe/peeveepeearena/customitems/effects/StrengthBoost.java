package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;

public class StrengthBoost implements ICustomItemEffect
{
	@Override
	public void performEffect(IPlayer player)
	{
		Buff.Combat.Damage.Increase.amplification(1).duration(20).applyTo(player);
	}
}
