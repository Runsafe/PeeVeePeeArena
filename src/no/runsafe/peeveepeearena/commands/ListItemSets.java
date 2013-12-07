package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.peeveepeearena.ShopItemSet;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.List;
import java.util.Map;

public class ListItemSets extends ExecutableCommand
{
	public ListItemSets(ShopRepository shopRepository)
	{
		super("listsets", "Lists all the available item sets in the shop", "runsafe.peeveepee.set.list");
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		List<ShopItemSet> itemSets = this.shopRepository.getAllSets();

		for (ShopItemSet itemSet : itemSets)
			executor.sendColouredMessage("&3%s - %s (%s)", itemSet.getID(), itemSet.getName(), itemSet.getCost());

		return null;
	}

	private final ShopRepository shopRepository;
}
