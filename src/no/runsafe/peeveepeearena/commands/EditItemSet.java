package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.HashMap;

public class EditItemSet extends PlayerCommand
{
	public EditItemSet(ShopRepository shopRepository)
	{
		super("editset", "Edits an item set.", "runsafe.peeveepee.set.edit", "id", "cost", "name");
		super.captureTail();
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
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
