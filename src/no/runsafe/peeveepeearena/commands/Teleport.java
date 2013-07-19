package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;

import java.util.Map;

public class Teleport extends PlayerCommand
{
	public Teleport(TeleportEngine teleportEngine)
	{
		super("teleport", "Teleports you to the PvP arena", "runsafe.peeveepee.teleport");
		this.teleportEngine = teleportEngine;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		this.teleportEngine.teleportToArena(executor);
		return null;
	}

	private final TeleportEngine teleportEngine;
}
