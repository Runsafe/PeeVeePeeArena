package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.argument.WholeNumber;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

public class CreateItemSet extends PlayerCommand
{
	public CreateItemSet(ShopRepository shopRepository)
	{
		super(
			"createset", "Creates an item set for the shop", "runsafe.peeveepee.set.create",
			new WholeNumber("cost").require(), new TrailingArgument("name")
		);
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String setName = parameters.getValue("name");
		int cost = parameters.getValue("cost");

		this.shopRepository.createItemSet(setName, cost, executor.getInventory());
		return String.format("&2The item set %s has been created.", setName);
	}

	private final ShopRepository shopRepository;
}
