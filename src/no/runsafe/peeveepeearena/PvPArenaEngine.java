package no.runsafe.peeveepeearena;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.RunsafeWorld;
import no.runsafe.framework.server.player.RunsafePlayer;

public class PvPArenaEngine implements IConfigurationChanged
{
	public PvPArenaEngine(IOutput output)
	{
		this.output = output;
	}

	public void broadcastMessage(String message)
	{
		this.output.broadcastColoured("&c[PvP] &f" + message);
	}

	public RunsafeWorld getPvPWorld()
	{
		return RunsafeServer.Instance.getWorld(this.pvpWorld);
	}

	public boolean isInPvPWorld(RunsafePlayer player)
	{
		return player.getWorld().getName().equals(this.pvpWorld);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorld = configuration.getConfigValueAsString("pvpWorld");
	}

	private IOutput output;
	private String pvpWorld;
}
