package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.ExecutableCommand;
import no.runsafe.framework.server.ICommandExecutor;
import no.runsafe.peeveepeearena.chestdrop.ChestDropHandler;

import java.util.HashMap;

public class ForcePvPChest extends ExecutableCommand
{
	public ForcePvPChest(ChestDropHandler chestDropHandler)
	{
		super("forcepvpchest", "Forces the PvP chest event into pre-event stage", "runsafe.peeveepee.chests.force");
		this.chestDropHandler = chestDropHandler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, HashMap<String, String> parameters)
	{
		if (this.chestDropHandler.forceStartEvent())
			executor.sendColouredMessage("&2The event has been force started.");
		else
			executor.sendColouredMessage("&cThe event is not at a stage that can be forced currently.");
		return null;
	}

	private ChestDropHandler chestDropHandler;
}
