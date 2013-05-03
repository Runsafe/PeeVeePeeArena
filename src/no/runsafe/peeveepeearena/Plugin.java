package no.runsafe.peeveepeearena;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.peeveepeearena.chestdrop.ChestDropHandler;
import no.runsafe.peeveepeearena.chestdrop.ChestDropRepository;
import no.runsafe.peeveepeearena.chestdrop.LootTableManager;
import no.runsafe.peeveepeearena.commands.CreateChestDrop;
import no.runsafe.peeveepeearena.events.CloseInventory;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		// Chest Drop
		addComponent(ChestDropHandler.class); // Handler
		addComponent(ChestDropRepository.class); // Repository
		addComponent(LootTableManager.class);

		// Events
		addComponent(CloseInventory.class);

		// Commands
		addComponent(CreateChestDrop.class);
	}
}
