package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.ExecutableCommand;
import no.runsafe.framework.server.ICommandExecutor;
import no.runsafe.peeveepeearena.ShopItemSet;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.HashMap;
import java.util.List;

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
		List<ShopItemSet> itemSets = this.shopRepository.getAllSets();

		for (ShopItemSet itemSet : itemSets)
			executor.sendColouredMessage(String.format("&3%s - %s (%s)", itemSet.getID(),  itemSet.getName(), itemSet.getCost()));

		return null;
	}

	private ShopRepository shopRepository;
}
