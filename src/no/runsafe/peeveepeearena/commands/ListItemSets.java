package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.ExecutableCommand;
import no.runsafe.framework.server.ICommandExecutor;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.HashMap;

public class ListItemSets extends ExecutableCommand
{
	public ListItemSets(ShopRepository shopRepository)
	{
		super("listsets", "Lists all the available item sets in the shop", "runsafe.peeveepeearena.set.list");
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, HashMap<String, String> parameters)
	{

		return null;
	}

	private ShopRepository shopRepository;
}
