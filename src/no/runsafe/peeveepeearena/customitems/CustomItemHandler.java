package no.runsafe.peeveepeearena.customitems;

import no.runsafe.framework.event.player.IPlayerRightClick;
import no.runsafe.framework.server.block.RunsafeBlock;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.item.meta.RunsafeMeta;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.PvPArenaEngine;
import no.runsafe.peeveepeearena.customitems.items.ICustomItem;

import java.util.HashMap;
import java.util.Set;

public class CustomItemHandler implements IPlayerRightClick
{
	public CustomItemHandler(ICustomItem[] customItems, PvPArenaEngine pvpEngine)
	{
		this.pvpEngine = pvpEngine;
		for (ICustomItem item : customItems)
			this.customItems.put(item.getItemName().toLowerCase(), item);
	}

	@Override
	public boolean OnPlayerRightClick(RunsafePlayer player, RunsafeItemStack usingItem, RunsafeBlock targetBlock)
	{
		if (usingItem != null && usingItem instanceof RunsafeMeta)
		{
			String itemName = ((RunsafeMeta)usingItem).getDisplayName();
			if (itemName != null)
			{
				itemName = itemName.toLowerCase();
				if (this.customItems.containsKey(itemName))
				{
					if (this.pvpEngine.isInPvPZone(player))
					{
						ICustomItem customItem = this.customItems.get(itemName);
						customItem.onUse(player);
						player.removeItem(usingItem.getItemType(), 1);
					}
					else
					{
						player.sendColouredMessage("&cYou cannot use that here.");
					}
					return false;
				}
			}
		}
		return true;
	}

	public Set<String> getCustomItems()
	{
		return this.customItems.keySet();
	}

	public boolean customItemExists(String customItemName)
	{
		return this.customItems.containsKey(customItemName.toLowerCase());
	}

	public void givePlayerCustomItem(RunsafePlayer player, String customItemName)
	{
		if (this.customItemExists(customItemName))
		{
			ICustomItem item = this.customItems.get(customItemName.toLowerCase());
			player.getInventory().addItems(item.getItem());
		}
	}

	private final HashMap<String, ICustomItem> customItems = new HashMap<String, ICustomItem>();
	private final PvPArenaEngine pvpEngine;
}
