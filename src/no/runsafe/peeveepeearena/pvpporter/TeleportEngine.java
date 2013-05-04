package no.runsafe.peeveepeearena.pvpporter;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.player.RunsafePlayer;
import org.bukkit.Material;

import java.util.Map;

public class TeleportEngine implements IConfigurationChanged
{
	public void teleportIntoArena(RunsafePlayer player)
	{
		RunsafeLocation newLocation = this.teleportPoint;
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
		return location.getBlock().getMaterialType().getMaterialId() == Material.AIR.getId();
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.teleportRadius = configuration.getConfigValueAsInt("teleporterRadius");
		Map<String, String> teleporterPoint = configuration.getConfigValuesAsMap("teleporterPosition");
		this.teleportPoint = new RunsafeLocation(
			RunsafeServer.Instance.getWorld(teleporterPoint.get("world")),
			Integer.valueOf(teleporterPoint.get("x")),
			Integer.valueOf(teleporterPoint.get("y")),
			Integer.valueOf(teleporterPoint.get("z"))
		);
	}

	private RunsafeLocation teleportPoint;
	private int teleportRadius;
}
