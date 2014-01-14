package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.peeveepeearena.customitems.CustomItemHandler;

public class PvPItems extends ExecutableCommand
{
	public PvPItems(CustomItemHandler customItemHandler)
	{
		super("items", "Lists all of the PvP items available.", "runsafe.peeveepeearena.item.list");
		this.customItemHandler = customItemHandler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		for (String itemName : this.customItemHandler.getCustomItems())
			executor.sendColouredMessage("&3%s", itemName);

		return null;
	}

	private final CustomItemHandler customItemHandler;
}
