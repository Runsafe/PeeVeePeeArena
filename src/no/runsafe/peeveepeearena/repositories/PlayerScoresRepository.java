package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.Repository;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerScoresRepository extends Repository
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

	public HashMap<String, Integer> getScores(RunsafePlayer player)
	{
		HashMap<String, Integer> scores = new HashMap<String, Integer>();
		Map<String, Object> data = this.database.QueryRow(
				"SELECT kills, deaths FROM peeveepee_scores WHERE playerName = ?", player.getName()
		);

		if (data != null)
		{
			scores.put("kills", Integer.valueOf((String) data.get("kills")));
			scores.put("deaths", Integer.valueOf((String) data.get("deaths")));
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
		return versions;
	}

	private IDatabase database;
}
