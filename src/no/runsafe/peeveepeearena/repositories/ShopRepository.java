package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.ISet;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.inventory.RunsafeInventoryType;
import no.runsafe.peeveepeearena.ShopItemSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopRepository extends Repository
{
	public ShopRepository(IDatabase database, IServer server)
	{
		this.database = database;
		this.server = server;
	}

	public String getTableName()
	{
		return "peeveepee_shop";
	}

	public boolean itemSetExists(int id)
	{
		return this.database.QueryInteger("SELECT ID FROM peeveepee_shop WHERE ID = ?", id) != null;
	}

	public ShopItemSet getItemSet(int id)
	{
		if (this.itemSetExists(id))
		{
			IRow data = this.database.QueryRow(
				"SELECT name, cost, items FROM peeveepee_shop WHERE ID = ?", id
			);

			RunsafeInventory itemHolder = server.createInventory(null, RunsafeInventoryType.CHEST);
			if (data != null)
				return new ShopItemSet(
					id,
					data.String("name"),
					data.Integer("cost"),
					data.String("items"),
					itemHolder
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
		ISet data = this.database.Query("SELECT ID, name, cost FROM peeveepee_shop");
		for (IRow node : data)
			itemSets.add(new ShopItemSet(node.Integer("ID"), node.String("name"), node.Integer("cost")));

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
	private final IServer server;
}
