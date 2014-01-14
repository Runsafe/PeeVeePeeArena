package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;

public class Teleport extends PlayerCommand
{
	public Teleport(TeleportEngine teleportEngine)
	{
		super("teleport", "Teleports you to the PvP arena", "runsafe.peeveepee.teleport");
		this.teleportEngine = teleportEngine;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		this.teleportEngine.teleportToArena(executor);
		return null;
	}

	private final TeleportEngine teleportEngine;
}
