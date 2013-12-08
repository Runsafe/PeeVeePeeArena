package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;

public class JarOfDirt implements ICustomItem
{
	public JarOfDirt(TeleportEngine teleportEngine)
	{
		this.teleportEngine = teleportEngine;
	}

	@Override
	public void onUse(IPlayer player)
	{
		this.teleportEngine.teleportIntoArena(player);
	}

	@Override
	public String getItemName()
	{
		return "Jar of Dirt";
	}

	@Override
	public RunsafeMeta getItem()
	{
		RunsafeMeta item = Item.Miscellaneous.ExperienceBottle.getItem();
		item.setAmount(1);
		item.setDisplayName(this.getItemName()).addLore("You've got a jar of dirt, guess what's inside it!");
		return item;
	}

	private final TeleportEngine teleportEngine;
}
