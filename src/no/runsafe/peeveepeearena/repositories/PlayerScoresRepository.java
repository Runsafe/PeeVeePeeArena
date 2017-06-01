package no.runsafe.peeveepeearena.repositories;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.customevents.RatingChangeEvent;

import javax.annotation.Nonnull;

public class PlayerScoresRepository extends Repository implements IConfigurationChanged
{
	@Nonnull
	@Override
	public String getTableName()
	{
		return "peeveepee_scores";
	}

	// KILL / DEATH COUNTS
	public IRow getScores(IPlayer player)
	{
		return this.database.queryRow(
			"SELECT kills, deaths FROM peeveepee_scores WHERE player = ?", player.getName()
		);
	}

	public void incrementKills(IPlayer player)
	{
		this.database.execute(
			"INSERT INTO peeveepee_scores (player, kills, deaths) VALUES(?,1,0) " +
				"ON DUPLICATE KEY UPDATE kills = kills + 1", player.getName()
		);
	}

	public void incrementDeaths(IPlayer player)
	{
		this.database.execute(
			"INSERT INTO peeveepee_scores (player, kills, deaths) VALUES(?,0,1) " +
				"ON DUPLICATE KEY UPDATE deaths = deaths + 1", player.getName()
		);
	}

	// RATING
	public int getRating(IPlayer player)
	{
		Integer rating = this.database.queryInteger(
			"SELECT rating FROM peeveepee_scores WHERE player = ?",
			player.getName()
		);
		return rating == null ? defaultRating : rating;
	}

	public void updateRating(IPlayer player, int newRating)
	{
		// Fire a rating change event
		new RatingChangeEvent(player, newRating).Fire();

		this.database.execute(
			"INSERT INTO peeveepee_scores (player, rating) VALUES(?, ?) " +
				"ON DUPLICATE KEY UPDATE rating = ?", player.getName(), newRating, newRating
		);
	}

	// POINTS
	public int getPoints(IPlayer player)
	{
		Integer points = this.database.queryInteger(
			"SELECT points FROM peeveepee_scores WHERE player= ?", player.getName()
		);
		return points == null ? 0 : points;
	}

	public void updatePoints(IPlayer player, int points)
	{
		this.database.execute(
			"INSERT INTO peeveepee_scores (player, points) VALUES(?, ?) " +
				"ON DUPLICATE KEY UPDATE points = points + ?", player.getName(), points, points
		);
	}

	@Nonnull
	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();

		update.addQueries(
			"CREATE TABLE `peeveepee_scores` (" +
				"`playerName` varchar(50) NOT NULL," +
				"`kills` int(10) NOT NULL," +
				"`deaths` int(10) NOT NULL," +
				"PRIMARY KEY (`playerName`)" +
				")"
		);

		update.addQueries(
			"ALTER TABLE `peeveepee_scores` ADD COLUMN `rating` INT(5) DEFAULT '1500' AFTER `deaths`, ADD COLUMN `points` INT(10) DEFAULT '0' AFTER `rating`",
			"ALTER TABLE `peeveepee_scores` CHANGE COLUMN `kills` `kills` int(10) DEFAULT '0' AFTER `playerName`, CHANGE COLUMN `deaths` `deaths` int(10) DEFAULT '0' AFTER `kills`"
		);

		update.addQueries(
			"ALTER TABLE `peeveepee_scores` " +
				"CHANGE COLUMN `kills` `kills` int(10) NOT NULL DEFAULT '0' AFTER `playerName`," +
				"CHANGE COLUMN `deaths` `deaths` int(10) NOT NULL DEFAULT '0' AFTER `kills`," +
				"CHANGE COLUMN `rating` `rating` int(5) NOT NULL DEFAULT '1500' AFTER `deaths`," +
				"CHANGE COLUMN `points` `points` int(10) NOT NULL DEFAULT '0' AFTER `rating`"
		);

		update.addQueries(
			String.format("ALTER TABLE `%s` CHANGE `playerName` `player` varchar(50) NOT NULL", getTableName())
		);

		return update;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.defaultRating = configuration.getConfigValueAsInt("defaultRating");
	}

	private int defaultRating;
}
