package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.Map;

public class CreateItemSet extends PlayerCommand
{
	public CreateItemSet(ShopRepository shopRepository)
	{
		super(
			"createset", "Creates an item set for the shop", "runsafe.peeveepee.set.create",
			new RequiredArgument("cost"), new TrailingArgument("name")
		);
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		String setName = parameters.get("name");
		int cost = Integer.parseInt(parameters.get("cost"));

		this.shopRepository.createItemSet(setName, cost, executor.getInventory());
		return String.format("&2The item set %s has been created.", setName);
	}

	private final ShopRepository shopRepository;
}
