package no.runsafe.peeveepeearena;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.worldguardbridge.WorldGuardInterface;

public class PvPArenaEngine implements IConfigurationChanged
{
	public PvPArenaEngine(IOutput output, WorldGuardInterface worldGuardInterface, IServer server)
	{
		this.output = output;
		this.worldGuardInterface = worldGuardInterface;
		this.server = server;
	}

	public void broadcastMessage(String message)
	{
		this.output.broadcastColoured("&c[PvP] &f" + message);
	}

	public IWorld getPvPWorld()
	{
		return server.getWorld(this.pvpWorld);
	}

	public boolean isInPvPWorld(IPlayer player)
	{
		IWorld playerWorld = player.getWorld();
		return playerWorld != null && playerWorld.getName().equals(this.pvpWorld);
	}

	public boolean isInPvPZone(IPlayer player)
	{
		return this.isInPvPWorld(player) && worldGuardInterface.getApplicableRegions(player).contains(this.pvpRegion);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.pvpWorld = configuration.getConfigValueAsString("pvpWorld");
		this.pvpRegion = configuration.getConfigValueAsString("pvpZoneRegion");
	}

	private final IOutput output;
	private String pvpWorld;
	private String pvpRegion;
	private final WorldGuardInterface worldGuardInterface;
	private final IServer server;
}
