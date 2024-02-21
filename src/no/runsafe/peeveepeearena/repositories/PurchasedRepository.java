package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.api.database.*;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.Purchase;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PurchasedRepository extends Repository
{
	@Nonnull
	@Override
	public String getTableName()
	{
		return "peeveepee_purchases";
	}

	public List<Purchase> getPurchases(IPlayer player)
	{
		List<Purchase> purchases = new ArrayList<>();
		ISet data = this.database.query("SELECT ID, setID FROM peeveepee_purchases WHERE player = ?", player.getUniqueId().toString());
		for (IRow node : data)
			purchases.add(new Purchase(node.Integer("ID"), node.Integer("setID")));

		return purchases;
	}

	public void deletePurchase(int id)
	{
		this.database.execute("DELETE FROM peeveepee_purchases WHERE ID = ?", id);
	}

	@Nonnull
	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();

		update.addQueries(
			"CREATE TABLE `peeveepee_purchases` (" +
				"`ID` int(10) NOT NULL AUTO_INCREMENT," +
				"`player` varchar(50) NOT NULL," +
				"`setID` int(10) NOT NULL," +
				"PRIMARY KEY (`ID`)" +
			")"
		);

		update.addQueries(
			String.format( // Usernames -> Unique IDs
				"UPDATE IGNORE `%s` SET `player` = " +
					"COALESCE((SELECT `uuid` FROM player_db WHERE `name`=`%s`.`player`), `player`) " +
					"WHERE length(`player`) != 36",
				getTableName(), getTableName()
			)
		);

		return update;
	}
}