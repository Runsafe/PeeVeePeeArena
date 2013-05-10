package no.runsafe.peeveepeearena;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.command.Command;
import no.runsafe.peeveepeearena.commands.GetRating;
import no.runsafe.peeveepeearena.commands.Teleport;
import no.runsafe.peeveepeearena.events.RightClickBlock;
import no.runsafe.peeveepeearena.events.SignChange;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;
import no.runsafe.peeveepeearena.repositories.PlayerRatingRepository;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(PvPArenaEngine.class);

		// Repositories
		this.addComponent(PlayerRatingRepository.class);

		// Teleport
		addComponent(TeleportEngine.class);

		// Commands
		Command pvp = new Command("pvp", "PvP related commands", null);
		pvp.addSubCommand(getInstance(Teleport.class));
		pvp.addSubCommand(getInstance(GetRating.class));
		this.addComponent(pvp);

		// Events
		addComponent(SignChange.class);
		addComponent(RightClickBlock.class);
	}
}
