package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.event.player.IPlayerRightClickSign;
import no.runsafe.framework.server.block.RunsafeSign;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;

public class RightClickBlock implements IPlayerRightClickSign
{
	public RightClickBlock(TeleportEngine teleportEngine)
	{
		this.teleportEngine = teleportEngine;
	}

	@Override
	public boolean OnPlayerRightClickSign(RunsafePlayer player, RunsafeItemStack usingItem, RunsafeSign sign)
	{
		if (sign.getLine(0).equalsIgnoreCase("[PvP Warp]"))
			this.teleportEngine.teleportIntoArena(player);

		return true;
	}

	private TeleportEngine teleportEngine;
}
