package no.runsafe.peeveepeearena.chestdrop;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.block.RunsafeBlock;
import no.runsafe.framework.server.block.RunsafeChest;
import no.runsafe.framework.timer.IScheduler;
import no.runsafe.peeveepeearena.PvPArenaEngine;
import org.bukkit.Effect;
import org.bukkit.Material;

import java.util.Map;

public class ChestDropHandler implements IConfigurationChanged
{
	public ChestDropHandler(IScheduler scheduler, IOutput output, PvPArenaEngine engine, LootTableManager lootTableManager)
	{
		this.scheduler = scheduler;
		this.output = output;
		this.engine = engine;
		this.lootTableManager = lootTableManager;

		this.setupNextEvent();
	}

	private void setupNextEvent()
	{
		this.output.fine("Scheduled next chest drop pre-event in " + this.chestDropEventCooldown + " seconds.");
		this.scheduler.startSyncTask(new Runnable()
		{
			@Override
			public void run()
			{
				runPreEvent();
			}
		}, this.chestDropEventCooldown);
	}

	public void runPreEvent()
	{
		this.output.fine("Running scheduled pre-event");
		this.output.fine("Scheduling chest drop event in " + this.chestDropPreEventTime + " seconds");
		this.scheduler.startSyncTask(new Runnable()
		{
			@Override
			public void run()
			{
				runEvent();
			}
		}, this.chestDropPreEventTime);
		this.engine.broadcastMessage("A treasure chest will spawn in the PvP arena soon!");
	}

	public void runEvent()
	{
		this.output.fine("Running scheduled chest drop event");
		this.setupNextEvent(); // Loop!

		this.scheduler.startSyncTask(new Runnable()
		{
			@Override
			public void run()
			{
				endEvent();
			}
		}, this.chestDropEventLength);

		this.chestLocation.getWorld().createExplosion(
				this.chestLocation.getX(),
				this.chestLocation.getY(),
				this.chestLocation.getZ(),
				4F, false, false
		);
		this.chestLocation.getWorld().strikeLightningEffect(this.chestLocation);

		RunsafeBlock block = this.chestLocation.getBlock();
		block.setTypeId(Material.CHEST.getId());

		RunsafeChest chest = (RunsafeChest) block.getBlockState();
		String loot = this.lootTableManager.getRandomLootTable();
		if (loot != null) chest.getInventory().unserialize(loot);
		this.engine.broadcastMessage("A treasure chest has spawned in the PvP arena!");
	}

	public void endEvent()
	{
		this.output.fine("Event ended, removing the loot chest.");
		this.chestLocation.getBlock().setTypeId(Material.AIR.getId());
		this.chestLocation.getWorld().playEffect(this.chestLocation, Effect.SMOKE, 0);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.chestDropEventCooldown = configuration.getConfigValueAsInt("chestDropTime");
		this.chestDropPreEventTime = configuration.getConfigValueAsInt("chestDropPreEventTime");
		this.chestDropEventLength = configuration.getConfigValueAsInt("chestDropEventLength");

		Map<String, String> location = configuration.getConfigValuesAsMap("chestDropLocation");
		this.chestLocation = new RunsafeLocation(
				RunsafeServer.Instance.getWorld(location.get("world")),
				Integer.valueOf(location.get("x")),
				Integer.valueOf(location.get("y")),
				Integer.valueOf(location.get("z"))
		);
	}

	private IScheduler scheduler;
	private IOutput output;
	private PvPArenaEngine engine;
	private int chestDropEventCooldown;
	private int chestDropPreEventTime;
	private int chestDropEventLength;
	private LootTableManager lootTableManager;
	private RunsafeLocation chestLocation;
}
