package no.runsafe.peeveepeearena.events;

import no.runsafe.framework.event.player.IPlayerDropItemEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerDropItemEvent;

public class PlayerDropItems implements IPlayerDropItemEvent
{
	@Override
	public void OnPlayerDropItem(RunsafePlayerDropItemEvent event)
	{
		event.getPlayer().getInventory().remove(event.getItem().getItemStack());
		event.setCancelled(true);
	}
}
