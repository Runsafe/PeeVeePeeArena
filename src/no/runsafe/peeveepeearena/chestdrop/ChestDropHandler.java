package no.runsafe.peeveepeearena.chestdrop;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.block.RunsafeBlock;
import no.runsafe.framework.server.block.RunsafeChest;
import no.runsafe.framework.timer.IScheduler;
import org.bukkit.Material;

import java.util.Map;

public class ChestDropHandler implements IConfigurationChanged
{
	public ChestDropHandler(IScheduler scheduler, IOutput output, LootTableManager lootTableManager)
	{
		this.scheduler = scheduler;
		this.output = output;
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

		RunsafeBlock block = this.chestLocation.getBlock();
		block.setTypeId(Material.CHEST.getId());

		RunsafeChest chest = (RunsafeChest) block.getBlockState();
		String loot = this.lootTableManager.getRandomLootTable();
		if (loot != null) chest.getInventory().unserialize(loot);
	}

	public void endEvent()
	{
		this.output.fine("Event ended, removing the loot chest.");
		this.chestLocation.getBlock().setTypeId(Material.AIR.getId());
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
	private int chestDropEventCooldown;
	private int chestDropPreEventTime;
	private int chestDropEventLength;
	private LootTableManager lootTableManager;
	private RunsafeLocation chestLocation;
}
