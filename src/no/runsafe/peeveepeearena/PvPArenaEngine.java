package no.runsafe.peeveepeearena;

import no.runsafe.framework.output.IOutput;

public class PvPArenaEngine
{
	public PvPArenaEngine(IOutput output)
	{
		this.output = output;
	}

	public void broadcastMessage(String message)
	{
		this.output.broadcastColoured("&c[PvP] &f" + message);
	}

	private IOutput output;
}
