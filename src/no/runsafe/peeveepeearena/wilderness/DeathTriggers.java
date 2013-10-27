package no.runsafe.peeveepeearena.wilderness;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.player.IPlayerCustomEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeathTriggers implements IConfigurationChanged, IPlayerCustomEvent
{
	@Override
	public void OnConfigurationChanged(IConfiguration config)
	{
		this.deathRegions = config.getConfigValueAsList("wilderness.deathRegions");
		this.wildernessWorld = config.getConfigValueAsString("wilderness.world");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void OnPlayerCustomEvent(RunsafeCustomEvent event)
	{
		if (event.getEvent().equals("region.enter"))
		{
			Map<String, String> data = (Map<String, String>) event.getData();
			RunsafePlayer player = event.getPlayer();
			RunsafeWorld world = player.getWorld();

			if (deathRegions.contains(data.get("region")) && data.get("world").equals(this.wildernessWorld))
			{
				//world.createExplosion(player.getLocation(), 5, false, false);
				player.damage(100.0D);
			}
		}
	}

	private List<String> deathRegions = new ArrayList<String>();
	private String wildernessWorld;
}
