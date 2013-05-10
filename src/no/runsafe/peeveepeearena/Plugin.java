package no.runsafe.peeveepeearena;

import no.runsafe.framework.RunsafeConfigurablePlugin;
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

		// Events
		addComponent(SignChange.class);
		addComponent(RightClickBlock.class);
	}
}
