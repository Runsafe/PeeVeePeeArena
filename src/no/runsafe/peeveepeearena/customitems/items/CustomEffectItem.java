package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.customitems.effects.ICustomItemEffect;

import java.util.ArrayList;
import java.util.List;

public class CustomEffectItem
{
	public void applyRandomEffect(RunsafePlayer player)
	{
		this.itemEffects.get((int) (Math.random() * 10) * itemEffects.size()).performEffect(player);
	}

	public void addEffect(ICustomItemEffect effect)
	{
		this.itemEffects.add(effect);
	}

	private List<ICustomItemEffect> itemEffects = new ArrayList<ICustomItemEffect>();
}
