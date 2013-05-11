package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.event.player.IPlayerDropItemEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerDropItemEvent;
import no.runsafe.framework.server.player.RunsafePlayer;

public class PlayerDropItems implements IPlayerDropItemEvent, IConfigurationChanged
{
	@Override
	public void OnPlayerDropItem(RunsafePlayerDropItemEvent event)
	{
		RunsafePlayer player = event.getPlayer();

		if (player.getWorld().getName().equals(this.pvpWorld))
		{
			player.getInventory().remove(event.getItem().getItemStack());
			event.setCancelled(true);
		}
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorld = configuration.getConfigValueAsString("pvpWorld");
	}

	private String pvpWorld;
}
