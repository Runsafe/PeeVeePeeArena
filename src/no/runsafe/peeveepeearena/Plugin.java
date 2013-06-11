package no.runsafe.peeveepeearena;

import no.runsafe.cheeves.AchievementFinder;
import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.command.Command;
import no.runsafe.mailbox.MailSender;
import no.runsafe.peeveepeearena.commands.*;
import no.runsafe.peeveepeearena.customitems.CustomItemHandler;
import no.runsafe.peeveepeearena.customitems.items.JarOfDirt;
import no.runsafe.peeveepeearena.customitems.items.SuspiciousMushroom;
import no.runsafe.peeveepeearena.events.PlayerDeath;
import no.runsafe.peeveepeearena.events.PlayerDropItems;
import no.runsafe.peeveepeearena.events.RightClickBlock;
import no.runsafe.peeveepeearena.events.SignChange;
import no.runsafe.peeveepeearena.pvpporter.TeleportEngine;
import no.runsafe.peeveepeearena.repositories.PlayerScoresRepository;
import no.runsafe.peeveepeearena.repositories.PurchasedRepository;
import no.runsafe.peeveepeearena.repositories.ShopRepository;
import no.runsafe.worldguardbridge.WorldGuardInterface;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		this.addComponent(Instances.get("RunsafeMailbox").getComponent(MailSender.class));
		this.addComponent(Instances.get("RunsafeWorldGuardBridge").getComponent(WorldGuardInterface.class));
		this.addComponent(Instances.get("Cheeves").getComponent(AchievementFinder.class));
		this.addComponent(PvPArenaEngine.class);
		this.addComponent(RatingHandler.class);

		// Repositories
		this.addComponent(PlayerScoresRepository.class);
		this.addComponent(ShopRepository.class);
		this.addComponent(PurchasedRepository.class);

		// Teleport
		this.addComponent(TeleportEngine.class);

		// Custom Items
		this.addComponent(CustomItemHandler.class);
		this.addComponent(JarOfDirt.class);
		this.addComponent(SuspiciousMushroom.class);

		// Commands
		Command pvp = new Command("pvp", "PvP related commands", null);
		pvp.addSubCommand(getInstance(Teleport.class));
		pvp.addSubCommand(getInstance(GetRating.class));
		pvp.addSubCommand(getInstance(GetScore.class));
		pvp.addSubCommand(getInstance(GetPoints.class));
		pvp.addSubCommand(getInstance(Checkout.class));
		this.addComponent(pvp);

		Command pvpAdmin = new Command("admin", "PvP admin related commands", null);
		pvpAdmin.addSubCommand(getInstance(CreateItemSet.class));
		pvpAdmin.addSubCommand(getInstance(DeleteItemSet.class));
		pvpAdmin.addSubCommand(getInstance(EditItemSet.class));
		pvpAdmin.addSubCommand(getInstance(GetItemSet.class));
		pvpAdmin.addSubCommand(getInstance(ListItemSets.class));
		pvpAdmin.addSubCommand(getInstance(PvPItems.class));
		pvpAdmin.addSubCommand(getInstance(PvPItem.class));

		pvp.addSubCommand(pvpAdmin);

		// Events
		this.addComponent(SignChange.class);
		this.addComponent(RightClickBlock.class);
		this.addComponent(PlayerDeath.class);
		this.addComponent(PlayerDropItems.class);
	}
}
