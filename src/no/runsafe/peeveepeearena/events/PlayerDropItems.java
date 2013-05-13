package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.event.player.IPlayerDropItemEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerDropItemEvent;

public class PlayerDropItems implements IPlayerDropItemEvent, IConfigurationChanged
{
	@Override
	public void OnPlayerDropItem(RunsafePlayerDropItemEvent event)
	{
		if (event.getPlayer().getWorld().getName().equals(this.pvpWorld))
			event.getItem().remove();
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorld = configuration.getConfigValueAsString("pvpWorld");
	}

	private String pvpWorld;
}
