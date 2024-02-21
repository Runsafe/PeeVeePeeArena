package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.block.ISignChange;
import no.runsafe.framework.api.player.IPlayer;

public class SignChange implements ISignChange
{
	@Override
	public boolean OnSignChange(IPlayer player, IBlock block, String[] text)
	{
		if (text[0].equalsIgnoreCase("[PVP Warp]"))
			return player.hasPermission("runsafe.peeveepee.teleport.create");

		return true;
	}
}
