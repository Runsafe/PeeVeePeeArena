package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.event.inventory.IInventoryClosed;
import no.runsafe.framework.server.event.inventory.RunsafeInventoryCloseEvent;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.chestdrop.LootTableManager;

public class CloseInventory implements IInventoryClosed
{
	public CloseInventory(LootTableManager lootTableManager)
	{
		this.lootTableManager = lootTableManager;
	}

	@Override
	public void OnInventoryClosed(RunsafeInventoryCloseEvent event)
	{
		RunsafePlayer player = event.getPlayer();

		if (this.lootTableManager.isCreatingLootTable(player))
			this.lootTableManager.stopCreatingLootTable(player);
	}

	private LootTableManager lootTableManager;
}
