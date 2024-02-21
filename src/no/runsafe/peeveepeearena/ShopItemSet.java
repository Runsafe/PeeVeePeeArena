package no.runsafe.peeveepeearena;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.List;

public class ShopItemSet
{
	public ShopItemSet(int ID, String name, int cost, String inventoryString, RunsafeInventory inventory)
	{
		this.ID = ID;
		this.name = name;
		this.cost = cost;

		inventory.unserialize(inventoryString);
		this.items = inventory;
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
			return true; // Not enough room in the players inventory.

		for (RunsafeMeta item : this.items.getContents())
			playerInventory.addItems(item);

		return false;
	}

	private final int ID;
	private final String name;
	private final int cost;
	private RunsafeInventory items;
}
