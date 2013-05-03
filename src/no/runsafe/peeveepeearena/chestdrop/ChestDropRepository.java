package no.runsafe.peeveepeearena.chestdrop;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestDropRepository extends Repository
{
	public ChestDropRepository(IDatabase database)
	{
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "pvp_loot_chests";
	}

	public String getRandomInventory()
	{
		Map<String, Object> data = this.database.QueryRow(
				"SELECT inventory FROM pvp_loot_chests AS r1 JOIN " +
					"(SELECT (RAND() * (SELECT MAX(ID) FROM pvp_loot_chests)) AS ID) " +
					"AS r2 WHERE r1.ID >= r2.ID ORDER BY r1.ID ASC LIMIT 1"
		);

		if (data == null)
			return null;

		return (String) data.get("inventory");
	}

	public void addInventory(String inventoryString)
	{
		this.database.Execute("INSERT INTO pvp_loot_chests (inventory) VALUES(?)", inventoryString);
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> versions = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();

		sql.add(
				"CREATE TABLE `pvp_loot_chests` (" +
						"`ID` int(10) unsigned NOT NULL AUTO_INCREMENT," +
						"`inventory` longtext," +
						"PRIMARY KEY (`ID`)" +
				")"
		);
		versions.put(1, sql);
		return versions;
	}

	private IDatabase database;
}
