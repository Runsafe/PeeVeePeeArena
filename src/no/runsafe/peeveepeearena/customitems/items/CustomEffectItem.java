package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.customitems.effects.ICustomItemEffect;

import java.util.ArrayList;
import java.util.List;

public class CustomEffectItem
{
	public void applyRandomEffect(IPlayer player)
	{
		this.itemEffects.get((int) (Math.random() * itemEffects.size())).performEffect(player);
	}

	public void addEffect(ICustomItemEffect effect)
	{
		this.itemEffects.add(effect);
	}

	private final List<ICustomItemEffect> itemEffects = new ArrayList<>();
}
