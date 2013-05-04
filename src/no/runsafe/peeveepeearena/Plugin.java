package no.runsafe.peeveepeearena;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.peeveepeearena.chestdrop.ChestDropHandler;
import no.runsafe.peeveepeearena.chestdrop.ChestDropRepository;
import no.runsafe.peeveepeearena.chestdrop.LootTableManager;
import no.runsafe.peeveepeearena.commands.CreateChestDrop;
import no.runsafe.peeveepeearena.events.CloseInventory;
import no.runsafe.peeveepeearena.events.RightClickBlock;
import no.runsafe.peeveepeearena.events.SignChange;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(PvPArenaEngine.class);

		// Teleport
		addComponent(TeleportEngine.class);

		// Chest Drop
		addComponent(ChestDropHandler.class); // Handler
		addComponent(ChestDropRepository.class); // Repository
		addComponent(LootTableManager.class);

		// Events
		addComponent(CloseInventory.class);
		addComponent(SignChange.class);
		addComponent(RightClickBlock.class);

		// Commands
		addComponent(CreateChestDrop.class);
	}
}
