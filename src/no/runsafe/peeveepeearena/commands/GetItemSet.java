package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.ShopItemSet;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

public class GetItemSet extends PlayerCommand
{
	public GetItemSet(ShopRepository shopRepository)
	{
		super(
			"getset", "Retrieves an item set from the shop", "runsafe.peeveepee.set.get",
			new RequiredArgument("id")
		);
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		ShopItemSet itemSet = this.shopRepository.getItemSet(Integer.parseInt(parameters.get("id")));

		if (itemSet == null)
			return "&cUnable to find an item set with that ID.";

		if (!itemSet.giveToPlayer(executor))
			return "&cThere is not enough space in your inventory to hold this set.";

		return String.format("&2The item set %s has been added to your inventory.", itemSet.getName());
	}

	private final ShopRepository shopRepository;
}
