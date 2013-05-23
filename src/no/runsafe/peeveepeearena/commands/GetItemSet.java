package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.ShopItemSet;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.HashMap;

public class GetItemSet extends PlayerCommand
{
	public GetItemSet(ShopRepository shopRepository)
	{
		super("getset", "Retrieves an item set from the shop", "runsafe.peeveepee.set.get", "id");
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		ShopItemSet itemSet = this.shopRepository.getItemSet(Integer.parseInt(parameters.get("id")));

		if (!itemSet.giveToPlayer(executor))
			return "&cThere is not enough space in your inventory to hold this set.";

		return String.format("&2The item set %s has been added to your inventory.", itemSet.getName());
	}

	private ShopRepository shopRepository;
}
