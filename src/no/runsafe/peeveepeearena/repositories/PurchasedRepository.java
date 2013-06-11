package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.internal.database.Repository;
import no.runsafe.framework.internal.database.Row;
import no.runsafe.framework.internal.database.Set;
import no.runsafe.peeveepeearena.Purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PurchasedRepository extends Repository
{
	public PurchasedRepository(IDatabase database)
	{
		this.database = database;
	}

	public String getTableName()
	{
		return "peeveepee_purchases";
	}

	public List<Purchase> getPurchases(String playerName)
	{
		List<Purchase> purchases = new ArrayList<Purchase>();
		Set data = this.database.Query("SELECT ID, setID FROM peeveepee_purchases WHERE player = ?", playerName);

		if (data != null)
			for (Row node : data)
				purchases.add(new Purchase(node.Integer("ID"), node.Integer("setID")));

		return purchases;
	}

	public void deletePurchase(int id)
	{
		this.database.Execute("DELETE FROM peeveepee_purchases WHERE ID = ?", id);
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> versions = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
				"CREATE TABLE `peeveepee_purchases` (" +
						"`ID` int(10) NOT NULL AUTO_INCREMENT," +
						"`player` varchar(50) NOT NULL," +
						"`setID` int(10) NOT NULL," +
						"PRIMARY KEY (`ID`)" +
						")"
		);
		versions.put(1, sql);
		return versions;
	}

	private final IDatabase database;
}
