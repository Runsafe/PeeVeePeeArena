package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.Repository;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.peeveepeearena.ShopItemSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopRepository extends Repository
{
	public ShopRepository(IDatabase database)
	{
		this.database = database;
	}

	public String getTableName()
	{
		return "peeveepee_shop";
	}

	public boolean itemSetExists(int id)
	{
		Map<String, Object> data = this.database.QueryRow("SELECT ID FROM peeveepee_shop WHERE ID = ?", id);
		return !data.isEmpty();
	}

	public ShopItemSet getItemSet(int id)
	{
		if (this.itemSetExists(id))
		{
			Map<String, Object> data = this.database.QueryRow(
					"SELECT name, cost, items FROM peeveepee_shop WHERE ID = ?", id
			);

			if (data != null)
				return new ShopItemSet(
					id,
					(String) data.get("name"),
					(Integer) data.get("cost"),
					(String) data.get("items")
				);
		}
		return null;
	}

	public boolean editItemSet(int id, String name, int cost, RunsafeInventory itemHolder)
	{
		if (!this.itemSetExists(id))
			return false;

		this.database.Execute(
				"UPDATE peeveepee_shop SET name = ?, cost = ?, items = ? WHERE ID = ?",
				name, cost, itemHolder.serialize(), id
		);
		return true;
	}

	public void createItemSet(String name, int cost, RunsafeInventory itemHolder)
	{
		this.database.Execute(
				"INSERT INTO peeveepee_shop (name, cost, items) VALUES(?, ?, ?)",
				name, cost, itemHolder.serialize()
		);
	}

	public boolean deleteItemSet(int id)
	{
		if (!this.itemSetExists(id))
			return false;

		this.database.Execute("DELETE FROM peeveepee_shop WHERE ID = ?", id);
		return true;
	}

	public List<ShopItemSet> getAllSets()
	{
		List<ShopItemSet> itemSets = new ArrayList<ShopItemSet>();
		List<Map<String, Object>> data = this.database.Query("SELECT ID, name, cost FROM peeveepee_shop");

		if (data != null)
			for (Map<String, Object> node : data)
				itemSets.add(new ShopItemSet((Integer) node.get("ID"), (String) node.get("name"), (Integer) node.get("cost")));

		return itemSets;
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> versions = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
				"CREATE TABLE `peeveepee_shop` (" +
						"`ID` int(10) NOT NULL AUTO_INCREMENT," +
						"`name` varchar(50) NOT NULL," +
						"`cost` int(10) NOT NULL," +
						"`items` longtext NOT NULL," +
						"PRIMARY KEY (`ID`)" +
						")"
		);
		versions.put(1, sql);
		return versions;
	}

	private final IDatabase database;
}
