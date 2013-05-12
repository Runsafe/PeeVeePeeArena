package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.Repository;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerRatingRepository extends Repository implements IConfigurationChanged
{
	public PlayerRatingRepository(IDatabase database)
	{
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "peeveepee_ratings";
	}

	public int getRating(RunsafePlayer player)
	{
		Map<String, Object> data = this.database.QueryRow(
			"SELECT rating FROM peeveepee_ratings WHERE playerName = ?", player.getName()
		);

		if (data != null)
			return (Integer) data.get("rating");

		return this.defaultRating;
	}

	public void updateRating(RunsafePlayer player, int newRating)
	{
		this.database.Execute(
			"INSERT INTO peeveepee_ratings (playerName, rating) VALUES(?, ?) " +
				"ON DUPLICATE KEY UPDATE rating = ?", player.getName(), newRating,  newRating
		);
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> versions = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
				"CREATE TABLE `peeveepee_ratings` (" +
						"`playerName` varchar(50) NOT NULL," +
						"`rating` int(5) NOT NULL," +
						"PRIMARY KEY (`playerName`)" +
				")"
		);
		versions.put(1, sql);
		return versions;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.defaultRating = configuration.getConfigValueAsInt("defaultRating");
	}

	private IDatabase database;
	private int defaultRating;
}
