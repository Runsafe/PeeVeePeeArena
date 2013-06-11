package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.internal.database.Repository;
import no.runsafe.framework.internal.database.Row;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerScoresRepository extends Repository implements IConfigurationChanged
{
	public PlayerScoresRepository(IDatabase database)
	{
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "peeveepee_scores";
	}

	// KILL / DEATH COUNTS
	public HashMap<String, Integer> getScores(RunsafePlayer player)
	{
		HashMap<String, Integer> scores = new HashMap<String, Integer>();
		Row data = this.database.QueryRow(
				"SELECT kills, deaths FROM peeveepee_scores WHERE playerName = ?", player.getName()
		);

		if (data != null)
		{
			scores.put("kills", data.Integer("kills"));
			scores.put("deaths", data.Integer("deaths"));
		}

		return scores;
	}

	public void incrementKills(RunsafePlayer player)
	{
		this.database.Execute(
			"INSERT INTO peeveepee_scores (playerName, kills, deaths) VALUES(?,1,0) " +
				"ON DUPLICATE KEY UPDATE kills = kills + 1", player.getName()
		);
	}

	public void incrementDeaths(RunsafePlayer player)
	{
		this.database.Execute(
				"INSERT INTO peeveepee_scores (playerName, kills, deaths) VALUES(?,0,1) " +
						"ON DUPLICATE KEY UPDATE deaths = deaths + 1", player.getName()
		);
	}

	// RATING
	public int getRating(RunsafePlayer player)
	{
		Row data = this.database.QueryRow(
				"SELECT rating FROM peeveepee_scores WHERE playerName = ?", player.getName()
		);

		if (data != null)
			return data.Integer("rating");

		return this.defaultRating;
	}

	public void updateRating(RunsafePlayer player, int newRating)
	{
		this.database.Execute(
				"INSERT INTO peeveepee_scores (playerName, rating) VALUES(?, ?) " +
						"ON DUPLICATE KEY UPDATE rating = ?", player.getName(), newRating,  newRating
		);
	}

	// POINTS
	public int getPoints(RunsafePlayer player)
	{
		Row data = this.database.QueryRow(
			"SELECT points FROM peeveepee_scores WHERE playerName = ?", player.getName()
		);

		if (data != null)
			return data.Integer("points");

		return 0;
	}

	public void updatePoints(RunsafePlayer player, int points)
	{
		this.database.Execute(
			"INSERT INTO peeveepee_scores (playerName, points) VALUES(?, ?) " +
					"ON DUPLICATE KEY UPDATE points = points + ?", player.getName(), points, points
		);
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> versions = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
				"CREATE TABLE `peeveepee_scores` (" +
						"`playerName` varchar(50) NOT NULL," +
						"`kills` int(10) NOT NULL," +
						"`deaths` int(10) NOT NULL," +
						"PRIMARY KEY (`playerName`)" +
						")"
		);
		versions.put(1, sql);

		ArrayList<String> update = new ArrayList<String>();
		update.add("ALTER TABLE `peeveepee_scores` ADD COLUMN `rating` INT(5) DEFAULT '1500' AFTER `deaths`, ADD COLUMN `points` INT(10) DEFAULT '0' AFTER `rating`;");
		update.add("ALTER TABLE `peeveepee_scores` CHANGE COLUMN `kills` `kills` int(10) DEFAULT '0' AFTER `playerName`, CHANGE COLUMN `deaths` `deaths` int(10) DEFAULT '0' AFTER `kills`;");
		versions.put(2, update);

		ArrayList<String> update_two = new ArrayList<String>();
		update_two.add("" +
				"ALTER TABLE `peeveepee_scores` " +
				"CHANGE COLUMN `kills` `kills` int(10) NOT NULL DEFAULT '0' AFTER `playerName`," +
				"CHANGE COLUMN `deaths` `deaths` int(10) NOT NULL DEFAULT '0' AFTER `kills`," +
				"CHANGE COLUMN `rating` `rating` int(5) NOT NULL DEFAULT '1500' AFTER `deaths`," +
				"CHANGE COLUMN `points` `points` int(10) NOT NULL DEFAULT '0' AFTER `rating`;");
		versions.put(3, update_two);


		return versions;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.defaultRating = configuration.getConfigValueAsInt("defaultRating");
	}

	private final IDatabase database;
	private int defaultRating;
}
