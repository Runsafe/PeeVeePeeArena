package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.customitems.CustomItemHandler;

public class PvPItem extends PlayerCommand
{
	public PvPItem(CustomItemHandler customItemHandler)
	{
		super("item", "Gives you one of the PvP items", "runsafe.peeveepeearena.item.give", new TrailingArgument("item"));
		this.customItemHandler = customItemHandler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String customItemName = parameters.getRequired("item");
		if (this.customItemHandler.customItemExists(customItemName))
		{
			this.customItemHandler.givePlayerCustomItem(executor, customItemName);
			return "&2Created " + customItemName;
		}
		return "&cThat item does not exist.";
	}

	private final CustomItemHandler customItemHandler;
}
