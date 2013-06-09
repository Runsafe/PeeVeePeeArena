package no.runsafe.peeveepeearena;

import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.framework.server.inventory.RunsafeInventoryType;
import no.runsafe.framework.server.item.meta.RunsafeMeta;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.List;

public class ShopItemSet
{
	public ShopItemSet(int ID, String name, int cost, String inventoryString)
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

	public boolean giveToPlayer(RunsafePlayer player)
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
