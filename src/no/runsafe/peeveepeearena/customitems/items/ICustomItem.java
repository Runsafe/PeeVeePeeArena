package no.runsafe.peeveepeearena.customitems.items;

import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.player.RunsafePlayer;

public interface ICustomItem
{
	public void onUse(RunsafePlayer player);
	public String getItemName();
	public RunsafeItemStack getItem();
}
