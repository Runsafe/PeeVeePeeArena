package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.HashMap;

public class Share extends PlayerCommand
{
	public Share()
	{
		super("share", "Share your PvP scores/ratings.", "runsafe.peeveepee.share");
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		// TODO: Implement
		return null;
	}
}
