package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;

public class PoisonSelf implements ICustomItemEffect
{
	@Override
	public void performEffect(IPlayer player)
	{
		Buff.Combat.Damage.Poison.amplification(1).duration(10).applyTo(player);
	}
}
