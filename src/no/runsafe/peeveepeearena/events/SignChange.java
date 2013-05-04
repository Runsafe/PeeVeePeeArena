package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.event.block.ISignChange;
import no.runsafe.framework.server.block.RunsafeBlock;
import no.runsafe.framework.server.player.RunsafePlayer;

public class SignChange implements ISignChange
{
	@Override
	public boolean OnSignChange(RunsafePlayer player, RunsafeBlock block, String[] text)
	{
		if (text[0].equalsIgnoreCase("[PVP Warp]"))
			if (!player.hasPermission("runsafe.peeveepee.teleport.create"))
				return false;

		return true;
	}
}
