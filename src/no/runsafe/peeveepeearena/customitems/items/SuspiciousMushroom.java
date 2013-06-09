package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.customitems.effects.BlindSelf;
import no.runsafe.peeveepeearena.customitems.effects.PoisonSelf;
import no.runsafe.peeveepeearena.customitems.effects.SpeedBoost;
import no.runsafe.peeveepeearena.customitems.effects.StrengthBoost;

public class SuspiciousMushroom extends CustomEffectItem implements ICustomItem
{
	public SuspiciousMushroom()
	{
		this.addEffect(new PoisonSelf());
		this.addEffect(new StrengthBoost());
		this.addEffect(new SpeedBoost());
		this.addEffect(new BlindSelf());
	}

	@Override
	public void onUse(RunsafePlayer player)
	{
		this.applyRandomEffect(player);
	}

	@Override
	public String getItemName()
	{
		return "Suspicious Mushroom";
	}

	@Override
	public RunsafeItemStack getItem()
	{
		RunsafeItemStack item = Item.Decoration.Mushroom.Red.getItem();
		item.setAmount(1);
		item.setDisplayName("Suspicious Mushroom").addLore("You're not sure what this does, but eating it might be fun.");
		return item;
	}

}
