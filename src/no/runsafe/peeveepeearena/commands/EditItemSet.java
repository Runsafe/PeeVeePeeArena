package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

public class EditItemSet extends PlayerCommand
{
	public EditItemSet(ShopRepository shopRepository)
	{
		super(
			"editset", "Edits an item set.", "runsafe.peeveepee.set.edit",
			new RequiredArgument("id"), new RequiredArgument("cost"), new TrailingArgument("name")
		);
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		boolean edit = this.shopRepository.editItemSet(
			Integer.parseInt(parameters.getValue("id")),
			parameters.getValue("name"),
			Integer.parseInt(parameters.getValue("cost")),
			executor.getInventory()
		);

		return (edit ? "&2The item set has been updated." : "&cUnable to find an item set with that ID.");
	}

	private final ShopRepository shopRepository;
}
