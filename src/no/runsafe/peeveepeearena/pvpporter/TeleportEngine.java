package no.runsafe.peeveepeearena.pvpporter;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;

public class TeleportEngine implements IConfigurationChanged
{
	public TeleportEngine(IConsole output)
	{
		this.console = output;
	}

	public void teleportIntoArena(IPlayer player)
	{
		if (!setup)
			return;

		ILocation newLocation = teleportPoint.clone();
		int highX = teleportPoint.getBlockX() + teleportRadius;
		int highZ = teleportPoint.getBlockZ() + teleportRadius;
		int lowX = teleportPoint.getBlockX() - teleportRadius;
		int lowZ = teleportPoint.getBlockZ() - teleportRadius;

		newLocation.setX(getRandom(lowX, highX));
		newLocation.setZ(getRandom(lowZ, highZ));

		while (!safeToTeleport(newLocation))
		{
			newLocation.setX(getRandom(lowX, highX));
			newLocation.setZ(getRandom(lowZ, highZ));
		}

		player.teleport(newLocation);
	}

	private int getRandom(int low, int high)
	{
		return low + (int) (Math.random() * ((high - low) + 1));
	}

	private boolean safeToTeleport(ILocation location)
	{
		return location.getBlock().is(Item.Unavailable.Air);
	}

	public void teleportToArena(IPlayer player)
	{
		if (setup)
			player.teleport(arenaPoint);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		IWorld pvpWorld = configuration.getConfigValueAsWorld("pvpWorld");

		if (pvpWorld == null)
		{
			setup = false;
			console.logError("Invalid world supplied: %s. Teleportation disabled.", configuration.getConfigValueAsString("pvpWorld"));
			return;
		}

		teleportRadius = configuration.getConfigValueAsInt("teleporterRadius");
		teleportPoint = configuration.getConfigValueAsLocation("teleporterPosition");
		arenaPoint = configuration.getConfigValueAsLocation("arenaTeleport");
		setup = true;
	}

	private boolean setup;
	private ILocation teleportPoint;
	private ILocation arenaPoint;
	private int teleportRadius;
	private final IConsole console;
}
