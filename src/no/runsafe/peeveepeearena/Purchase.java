package no.runsafe.peeveepeearena;

public class Purchase
{
	public Purchase(int purchaseID, int setID)
	{
		this.purchaseID = purchaseID;
		this.setID = setID;
	}

	public int getPurchaseID()
	{
		return this.purchaseID;
	}

	public int getSetID()
	{
		return this.setID;
	}

	private final int purchaseID;
	private final int setID;
}
