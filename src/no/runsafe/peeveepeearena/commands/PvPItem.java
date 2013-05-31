package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.customitems.CustomItemHandler;

import java.util.HashMap;

public class PvPItem extends PlayerCommand
{
	public PvPItem(CustomItemHandler customItemHandler)
	{
		super("item", "Gives you one of the PvP items", "runsafe.peeveepeearena.item.give", "item");
		this.customItemHandler = customItemHandler;
		this.captureTail();
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		String customItemName = parameters.get("item");
		if (this.customItemHandler.customItemExists(customItemName))
		{
			this.customItemHandler.givePlayerCustomItem(executor, customItemName);
			return "&2Created " + customItemName;
		}
		return "&cThat item does not exist.";
	}

	private CustomItemHandler customItemHandler;
}
