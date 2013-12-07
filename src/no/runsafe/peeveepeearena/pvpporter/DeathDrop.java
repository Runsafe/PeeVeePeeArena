package no.runsafe.peeveepeearena.pvpporter;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerCustomEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.peeveepeearena.PvPArenaEngine;

import java.util.Map;

public class DeathDrop implements IConfigurationChanged, IPlayerCustomEvent
{
	public DeathDrop(PvPArenaEngine engine)
	{
		this.engine = engine;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		noEntryRegion = configuration.getConfigValueAsString("noEntryRegion");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void OnPlayerCustomEvent(RunsafeCustomEvent event)
	{
		if (event.getEvent().equals("region.enter"))
		{
			Map<String, String> data = (Map<String, String>) event.getData();
			IPlayer player = event.getPlayer();
			IWorld playerWorld = player.getWorld();

			if (playerWorld != null && playerWorld.isWorld(engine.getPvPWorld()) && data.get("region").equals(noEntryRegion))
				player.damage(100.0D); // Carrrrrl, that kills people.
		}
	}

	private String noEntryRegion;
	private PvPArenaEngine engine;
}
