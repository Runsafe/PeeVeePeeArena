package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.event.player.IPlayerDeathEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerDeathEvent;
import no.runsafe.framework.server.item.RunsafeItemStack;

import java.util.ArrayList;

public class PlayerDeath implements IConfigurationChanged, IPlayerDeathEvent
{
	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		if (event.getEntity().getWorld().getName().equals(pvpWorldName))
			event.setDrops(new ArrayList<RunsafeItemStack>());
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorldName = configuration.getConfigValueAsString("pvpWorld");
	}

	private String pvpWorldName;
}
