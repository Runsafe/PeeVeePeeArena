package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerDropItemEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerDropItemEvent;

public class PlayerDropItems implements IPlayerDropItemEvent, IConfigurationChanged
{
	@Override
	public void OnPlayerDropItem(RunsafePlayerDropItemEvent event)
	{
		IWorld world = event.getPlayer().getWorld();
		if (world != null && world.getName().equals(this.pvpWorld))
			event.getItem().remove();
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorld = configuration.getConfigValueAsString("pvpWorld");
	}

	private String pvpWorld;
}
