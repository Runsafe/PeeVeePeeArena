package no.runsafe.peeveepeearena;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.worldguardbridge.WorldGuardInterface;

public class PvPArenaEngine implements IConfigurationChanged
{
	public PvPArenaEngine(IOutput output, WorldGuardInterface worldGuardInterface)
	{
		this.output = output;
		this.worldGuardInterface = worldGuardInterface;
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
		RunsafeWorld playerWorld = player.getWorld();
		return playerWorld != null && playerWorld.getName().equals(this.pvpWorld);
	}

	public boolean isInPvPZone(RunsafePlayer player)
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
}
