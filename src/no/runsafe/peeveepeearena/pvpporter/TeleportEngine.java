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
		if (!this.setup)
			return;

		ILocation newLocation = this.teleportPoint.clone();
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
		return low + (int) (Math.random() * ((high - low) + 1));
	}

	private boolean safeToTeleport(ILocation location)
	{
		return location.getBlock().is(Item.Unavailable.Air);
	}

	public void teleportToArena(IPlayer player)
	{
		if (this.setup)
			player.teleport(this.arenaPoint);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		IWorld pvpWorld = configuration.getConfigValueAsWorld("pvpWorld");

		if (pvpWorld == null)
		{
			this.setup = false;
			this.console.logError("Invalid world supplied: %s. Teleportation disabled.", configuration.getConfigValueAsString("pvpWorld"));
			return;
		}

		this.teleportRadius = configuration.getConfigValueAsInt("teleporterRadius");
		this.teleportPoint = configuration.getConfigValueAsLocation("teleporterPosition");
		this.arenaPoint = configuration.getConfigValueAsLocation("arenaTeleport");
		this.setup = true;
	}

	private boolean setup;
	private ILocation teleportPoint;
	private ILocation arenaPoint;
	private int teleportRadius;
	private final IConsole console;
}
