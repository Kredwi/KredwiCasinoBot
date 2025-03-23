package ru.kredwi.casinobot.sql;

public class DBUser {
	private String username;
	private String discordId;
	private float balance;
	private int win;
	private int lose;
	
	public DBUser(String username, String discordId, float balance, int win, int lose) {
		this.username = username;
		this.discordId = discordId;
		this.balance = balance;
		this.win = win;
		this.lose = lose;
	}

	public String getUsername() {
		return username;
	}

	public String getDiscordId() {
		return discordId;
	}

	public float getBalance() {
		return balance;
	}
	public int getWin() {
		return win;
	}
	public int getLose() {
		return lose;
	}
}
