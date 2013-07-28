package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.Map;

public class EditItemSet extends PlayerCommand
{
	public EditItemSet(ShopRepository shopRepository)
	{
		super("editset", "Edits an item set.", "runsafe.peeveepee.set.edit", "id", "cost", new TrailingArgument("name"));
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		boolean edit = this.shopRepository.editItemSet(
			Integer.parseInt(parameters.get("id")),
			parameters.get("name"),
			Integer.parseInt(parameters.get("cost")),
			executor.getInventory()
		);

		return (edit ? "&2The item set has been updated." : "&cUnable to find an item set with that ID.");
	}

	private final ShopRepository shopRepository;
}
