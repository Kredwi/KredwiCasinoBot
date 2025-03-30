package ru.kredwi.casinobot.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.User;

public class JDBCActions {
	
	private static Connection connection;
	private static final String BALANCE_COLUMN = "balance";
	private static final String WIN_COLUMN = "win";
	private static final String LOSE_COLUMN = "lose";
	private static final String SALARY_COLUMN = "salary";
	private static final String LANGUAGE_COLUMN = "language";
	
	static {
		try {
			connection = MySQLConnector.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// auto increment is upping
	// i rewrite this in next commit
	public static final boolean addIfNotExistsUser(String language, String username, long discordId) {
		String sql = "INSERT IGNORE INTO `users` (language, username, discord_id, salary) "
				+ "VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setString(1, language);
			statement.setString(2, username);
			statement.setString(3, String.valueOf(discordId));
			statement.setDate(4, Date.valueOf(LocalDate.now()));

			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	private static final boolean updateUserInformation(String column, long discordId, Object number, char operation) {
		
		String sql = MessageFormat.format("UPDATE `users` SET {0} = {0} {1} ? WHERE `discord_id` = ?", column, operation);
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setObject(1, number);
			statement.setString(2, String.valueOf(discordId));
			
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static final Object getUserInformationFromColumn(String column, User user) {
		try (PreparedStatement statement = connection.prepareStatement("SELECT `" + column +"` FROM `users` WHERE `discord_id` = ?")) {
			statement.setString(1, String.valueOf(user.getIdLong()));
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) return resultSet.getObject(column);
				else {
					addIfNotExistsUser(null, user.getName(), user.getIdLong());
					return -1;
				}	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public static final boolean addUserBalance(long discordId, double addBalance) {
		return updateUserInformation(BALANCE_COLUMN, discordId, addBalance, '+');
	}
	public static final boolean addUserWin(long discordId, int addWins) {
		return updateUserInformation(WIN_COLUMN, discordId, addWins, '+');
	}
	public static final boolean addUserLose(long discordId, int addLose) {
		return updateUserInformation(LOSE_COLUMN, discordId, addLose, '+');
	}
	
	public static final boolean setUserDateSalary(User user, Timestamp date) {
		
		try (PreparedStatement statement = connection.prepareStatement("UPDATE `users` SET salary = ? WHERE discord_id = ?")) {
			
			statement.setTimestamp(1, date);
			statement.setString(2, String.valueOf(user.getIdLong()));
			
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static final boolean setUserLanguage(User user, String lang) {
		
		try (PreparedStatement statement = connection.prepareStatement("UPDATE `users` SET language = ? WHERE discord_id = ?")) {
			
			statement.setString(1, lang);
			statement.setString(2, String.valueOf(user.getIdLong()));
			
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static final boolean deleteUserBalance(long discordId, double deleteBalance) {
		return updateUserInformation(BALANCE_COLUMN, discordId, deleteBalance, '-');
	}
	
	public static final List<DBUser> getWithTopBalance() {
		List<DBUser> topUsers = new ArrayList<DBUser>();
		try (PreparedStatement statement = connection.prepareStatement("SELECT `username`, `discord_id`, `balance`, `win`, `lose` FROM `users` ORDER BY `balance` DESC LIMIT 5");
				ResultSet result = statement.executeQuery()) {
			
			while (result.next()) {
				
				String username = result.getString("username");
				String discordId = result.getString("discord_id");
				
				float balance = result.getFloat("balance");
				
				int win = result.getInt("win");
				int lose = result.getInt("lose");
				
				DBUser user = new DBUser(username, discordId, balance, win, lose);
				
				topUsers.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topUsers;
	}
	
	public static final int getUserWin(User user) {
		return (int)getUserInformationFromColumn(WIN_COLUMN, user);
	}
	public static final int getUserLose(User user) {
		return (int)getUserInformationFromColumn(LOSE_COLUMN, user);
	}
	public static final float getUserBalance(User user) {
		try {
			return (float)getUserInformationFromColumn(BALANCE_COLUMN, user);	
		} catch (ClassCastException e) {
			return 0F;
		}
		
	}
	public static final Timestamp getUserDateSalary(User user) {
		return (Timestamp) getUserInformationFromColumn(SALARY_COLUMN, user);
	}
	public static final String getUserLanguage(User user) {
		return (String) getUserInformationFromColumn(LANGUAGE_COLUMN, user);
	}
}
