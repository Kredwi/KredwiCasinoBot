package ru.kredwi.casinobot.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ru.kredwi.casinobot.CasinoBot;

public class DBInitilization {
	public void init() {
		MySQLConnector connector = MySQLConnector.getInstance();
		try (Connection connection = connector.getConnection();) {
			createTablesIfNotExits(connection);
		} catch (SQLException e) {
			CasinoBot.getLogger().info("ERROR DB INITILIZATION");
			CasinoBot.getLogger().info(e.getMessage());
		}
	}
	
	private void createTablesIfNotExits(Connection c) {
		try (Statement s = c.createStatement()) {
			final String usersSQL = "CREATE TABLE IF NOT EXISTS `users` (" +
			"`id` INT AUTO_INCREMENT PRIMARY KEY," +
			"`language` VARCHAR(256) NULL," +
			"`username` VARCHAR(256) NOT NULL," +
			"`discord_id` VARCHAR(256) NOT NULL UNIQUE," +
			"`balance` FLOAT DEFAULT 0.00," +
			"`win` INT DEFAULT 0," +
			"`lose` INT DEFAULT 0," +
			"`salary` TIMESTAMP NOT NULL"
			+ ")";
			
			final String gamesSQL = "CREATE TABLE IF NOT EXISTS `games` (" +
			"`id` INT AUTO_INCREMENT PRIMARY KEY," +
			"`game_id` VARCHAR(256) NULL," +
			"`discord_id` VARCHAR(256) NOT NULL UNIQUE," +
			"`is_win` BOOLEAN NOT NULL," +
			"`end_game` TIMESTAMP NOT NULL"
			+ ")";
			
			final String konfSQL = "CREATE TABLE IF NOT EXISTS `konf` (" +
			"`game_name` VARCHAR(256) NOT NULL," +
			"`konf` FLOAT NOT NULL"
			+ ")";
			
			CasinoBot.getLogger().info("Create users tables (if exists)");
			s.execute(usersSQL);
			
//			CasinoBot.getLogger().info("Create games table (if exists)");
//			s.execute(gamesSQL);
			
//			CasinoBot.getLogger().info("Create konf tables (if exists)");
//			s.execute(konfSQL);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}