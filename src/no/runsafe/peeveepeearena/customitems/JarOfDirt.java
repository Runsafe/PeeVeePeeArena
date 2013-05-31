package no.runsafe.peeveepeearena.customitems;

import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.item.meta.RunsafeItemMeta;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;
import org.bukkit.Material;

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
		RunsafeItemStack item = new RunsafeItemStack(Material.EXP_BOTTLE.getId());
		RunsafeItemMeta meta = item.getItemMeta();
		meta.addLore("You've got a jar of dirt, guess what's inside it!");
		item.setItemMeta(meta);

		return item;
	}

	private TeleportEngine teleportEngine;
}
