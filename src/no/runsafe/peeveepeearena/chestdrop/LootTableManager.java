package no.runsafe.peeveepeearena.chestdrop;

import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.HashMap;

public class LootTableManager
{
	public LootTableManager(ChestDropRepository repository)
	{
		this.repository = repository;
	}

	public void startCreatingLootTable(RunsafePlayer player)
	{
		RunsafeInventory inventory = RunsafeServer.Instance.createInventory(null, 27, "New Loot Table");
		player.openInventory(inventory); // Show the player a new inventory.
		this.playersCreating.put(player.getName(), inventory);
	}

	public boolean isCreatingLootTable(RunsafePlayer player)
	{
		return this.playersCreating.containsKey(player.getName());
	}

	public void stopCreatingLootTable(RunsafePlayer player)
	{
		if (isCreatingLootTable(player))
		{
			String playerName = player.getName();
			this.repository.addInventory(this.playersCreating.get(playerName).serialize());
			this.playersCreating.remove(playerName);
		}
	}

	public String getRandomLootTable()
	{
		return this.repository.getRandomInventory();
	}

	private HashMap<String, RunsafeInventory> playersCreating = new HashMap<String, RunsafeInventory>();
	private ChestDropRepository repository;
}
