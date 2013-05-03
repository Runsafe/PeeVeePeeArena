package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.chestdrop.LootTableManager;

import java.util.HashMap;

public class CreateChestDrop extends PlayerCommand
{
	public CreateChestDrop(LootTableManager lootTableManager)
	{
		super("createchestdrop", "Creates a PvP chest loot-table", "runsafe.peeveepee.chests.create");
		this.lootTableManager = lootTableManager;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		this.lootTableManager.startCreatingLootTable(executor);
		return null;
	}

	private LootTableManager lootTableManager;
}
