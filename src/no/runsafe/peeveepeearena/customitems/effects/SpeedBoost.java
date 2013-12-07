package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;

public class SpeedBoost implements ICustomItemEffect
{
	@Override
	public void performEffect(IPlayer player)
	{
		Buff.Utility.Movement.IncreaseSpeed.amplification(3).duration(15).applyTo(player);
	}
}
