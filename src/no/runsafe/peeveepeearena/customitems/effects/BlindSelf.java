package no.runsafe.peeveepeearena.customitems.effects;

import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.framework.server.potion.RunsafePotionEffect;
import no.runsafe.framework.server.potion.RunsafePotionEffectType;

public class BlindSelf implements ICustomItemEffect
{
	@Override
	public void performEffect(RunsafePlayer player)
	{
		player.addPotionEffect(RunsafePotionEffect.create(RunsafePotionEffectType.BLINDNESS, 10, 60));
	}
}
