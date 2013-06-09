package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.server.item.meta.RunsafeMeta;
import no.runsafe.framework.server.player.RunsafePlayer;

public interface ICustomItem
{
	public void onUse(RunsafePlayer player);

	public String getItemName();

	public RunsafeMeta getItem();
}
