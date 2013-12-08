package no.runsafe.peeveepeearena;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.inventory.RunsafeInventoryType;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.List;

public class ShopItemSet
{
	public ShopItemSet(int ID, String name, int cost, String inventoryString, IServer server)
	{
		this.ID = ID;
		this.name = name;
		this.cost = cost;

		RunsafeInventory itemHolder = RunsafeServer.Instance.createInventory(null, RunsafeInventoryType.CHEST);
		itemHolder.unserialize(inventoryString);
		this.items = itemHolder;
	}

	public ShopItemSet(int ID, String name, int cost)
	{
		this.ID = ID;
		this.name = name;
		this.cost = cost;
	}

	public int getID()
	{
		return this.ID;
	}

	public String getName()
	{
		return this.name;
	}

	public int getCost()
	{
		return this.cost;
	}

	public boolean giveToPlayer(IPlayer player)
	{
		RunsafeInventory playerInventory = player.getInventory();
		List<RunsafeMeta> setContents = this.items.getContents();

		if (playerInventory.getSize() - playerInventory.getContents().size() < setContents.size())
			return false; // Not enough room in the players inventory.

		for (RunsafeMeta item : this.items.getContents())
			playerInventory.addItems(item);

		return true;
	}

	private final int ID;
	private final String name;
	private final int cost;
	private RunsafeInventory items;
}
