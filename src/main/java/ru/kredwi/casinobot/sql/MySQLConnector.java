package ru.kredwi.casinobot.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ru.kredwi.casinobot.CasinoBot;

public class MySQLConnector {
	
	private static MySQLConnector instance = null;
	
	private HikariConfig config = new HikariConfig();
	private HikariDataSource dataSource = null;
	
	private String username = CasinoBot.config.getProperty("database.username");
	private String password = CasinoBot.config.getProperty("database.password");
	private String port = CasinoBot.config.getProperty("database.port");
	private String host = CasinoBot.config.getProperty("database.host");
	private String dbName = CasinoBot.config.getProperty("database.name");
	
	private MySQLConnector() {
		try {
			createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};
	
	public static synchronized MySQLConnector getInstance() {
		if (instance == null) {
			instance = new MySQLConnector();
		}
		return instance;
	}
	
	private void createConnection() throws SQLException {
		String url = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}", host, port, dbName);
		this.config.setJdbcUrl(url);
		this.config.setUsername(username);
		this.config.setPassword(password != null ? password : "");
		this.config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		this.config.setMaximumPoolSize(5);
		
		this.dataSource = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException {
		if (dataSource == null) {
			this.createConnection();
		}
		return this.dataSource.getConnection();
	}
	
	public void closeDataSource() {
		if (dataSource != null) {
			this.dataSource.close();
		}
	}
}

