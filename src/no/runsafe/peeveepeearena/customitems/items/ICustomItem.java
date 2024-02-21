package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public interface ICustomItem
{
	void onUse(IPlayer player);

	String getItemName();

	RunsafeMeta getItem();
}
