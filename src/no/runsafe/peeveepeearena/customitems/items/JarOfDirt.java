package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.item.meta.RunsafeMeta;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;

public class JarOfDirt implements ICustomItem
{
	public JarOfDirt(TeleportEngine teleportEngine)
	{
		this.teleportEngine = teleportEngine;
	}

	@Override
	public void onUse(RunsafePlayer player)
	{
		this.teleportEngine.teleportIntoArena(player);
	}

	@Override
	public String getItemName()
	{
		return "Jar of Dirt";
	}

	@Override
	public RunsafeItemStack getItem()
	{
		RunsafeMeta item = Item.Miscellaneous.Beacon.getMeta();
		item.setAmount(1);
		item.setDisplayName(this.getItemName()).addLore("You've got a jar of dirt, guess what's inside it!");
		return item;
	}

	private final TeleportEngine teleportEngine;
}
