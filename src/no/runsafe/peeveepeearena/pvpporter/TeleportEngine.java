package no.runsafe.peeveepeearena.pvpporter;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class TeleportEngine implements IConfigurationChanged
{
	public void teleportIntoArena(RunsafePlayer player)
	{
		RunsafeLocation newLocation = new RunsafeLocation(
				this.teleportPoint.getWorld(),
				this.teleportPoint.getX(),
				this.teleportPoint.getY(),
				this.teleportPoint.getZ()
		);
		int highX = this.teleportPoint.getBlockX() + this.teleportRadius;
		int highZ = this.teleportPoint.getBlockZ() + this.teleportRadius;
		int lowX = this.teleportPoint.getBlockX() - this.teleportRadius;
		int lowZ = this.teleportPoint.getBlockZ() - this.teleportRadius;

		newLocation.setX(this.getRandom(lowX, highX));
		newLocation.setZ(this.getRandom(lowZ, highZ));

		while (!this.safeToTeleport(newLocation))
		{
			newLocation.setX(this.getRandom(lowX, highX));
			newLocation.setZ(this.getRandom(lowZ, highZ));
		}

		player.teleport(newLocation);
	}

	private int getRandom(int low, int high)
	{
		return low + (int)(Math.random() * ((high - low) + 1));
	}

	private boolean safeToTeleport(RunsafeLocation location)
	{
		return location.getBlock().is(Item.Unavailable.Air);
	}

	public void teleportToArena(RunsafePlayer player)
	{
		player.teleport(this.arenaPoint);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		RunsafeWorld pvpWorld = RunsafeServer.Instance.getWorld(configuration.getConfigValueAsString("pvpWorld"));

		this.teleportRadius = configuration.getConfigValueAsInt("teleporterRadius");
		Map<String, String> teleporterPoint = configuration.getConfigValuesAsMap("teleporterPosition");
		this.teleportPoint = new RunsafeLocation(
			pvpWorld,
			Integer.valueOf(teleporterPoint.get("x")),
			Integer.valueOf(teleporterPoint.get("y")),
			Integer.valueOf(teleporterPoint.get("z"))
		);

		Map<String, String> arenaPoint = configuration.getConfigValuesAsMap("arenaTeleport");
		this.arenaPoint = new RunsafeLocation(
			pvpWorld,
			Integer.valueOf(arenaPoint.get("x")),
			Integer.valueOf(arenaPoint.get("y")),
			Integer.valueOf(arenaPoint.get("z"))
		);
	}

	private RunsafeLocation teleportPoint;
	private RunsafeLocation arenaPoint;
	private int teleportRadius;
}
