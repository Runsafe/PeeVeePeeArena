package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.api.event.player.IPlayerRightClickSign;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.block.RunsafeSign;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;

public class RightClickBlock implements IPlayerRightClickSign
{
	public RightClickBlock(TeleportEngine teleportEngine)
	{
		this.teleportEngine = teleportEngine;
	}

	@Override
	public boolean OnPlayerRightClickSign(IPlayer player, RunsafeMeta usingItem, RunsafeSign sign)
	{
		if (sign.getLine(0).equalsIgnoreCase("[PvP Warp]"))
			this.teleportEngine.teleportIntoArena(player);

		return true;
	}

	private final TeleportEngine teleportEngine;
}
