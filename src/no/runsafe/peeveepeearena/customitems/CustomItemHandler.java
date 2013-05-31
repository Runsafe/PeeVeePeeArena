package no.runsafe.peeveepeearena.customitems;

import no.runsafe.framework.event.player.IPlayerRightClick;
import no.runsafe.framework.server.block.RunsafeBlock;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.item.meta.RunsafeItemMeta;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.HashMap;
import java.util.Set;

public class CustomItemHandler implements IPlayerRightClick
{
	public CustomItemHandler(ICustomItem[] customItems)
	{
		for (ICustomItem item : customItems)
			this.customItems.put(item.getItemName(), item);
	}

	@Override
	public boolean OnPlayerRightClick(RunsafePlayer player, RunsafeItemStack usingItem, RunsafeBlock targetBlock)
	{
		if (usingItem != null)
		{
			RunsafeItemMeta meta = usingItem.getItemMeta();
			if (meta != null)
			{
				String itemName = meta.getDisplayName();
				if (itemName != null)
				{
					if (this.customItems.containsKey(itemName))
					{
						ICustomItem customItem = this.customItems.get(itemName);
						customItem.onUse(player);
						return false;
					}
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
		return this.customItems.containsKey(customItemName);
	}

	public void givePlayerCustomItem(RunsafePlayer player, String customItemName)
	{
		if (this.customItemExists(customItemName))
		{
			ICustomItem item = this.customItems.get(customItemName);
			player.getInventory().addItems(item.getItem());
		}
	}

	private HashMap<String, ICustomItem> customItems = new HashMap<String, ICustomItem>();
}
