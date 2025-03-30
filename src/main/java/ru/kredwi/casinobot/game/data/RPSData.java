package ru.kredwi.casinobot.game.data;

import net.dv8tion.jda.api.entities.User;
import ru.kredwi.casinobot.enums.RPSEnum;

public class RPSData {
	
	private final String gameID;
	private final User player;
	private final User opponent;
	private final double deposit;
	
	private RPSEnum playerChoice = null;
	private RPSEnum opponentChoice = null;
	
	public RPSData(String gameID, User player, User opponent, double deposit) {
		this.gameID = gameID;
		this.player = player;
		this.opponent = opponent;
		this.deposit = deposit;
	}

	public String getGameID() {
		return gameID;
	}

	public User getOpponent() {
		return opponent;
	}

	public User getPlayer() {
		return player;
	}

	public double getDeposit() {
		return deposit;
	}

	public RPSEnum getOpponentChoice() {
		return opponentChoice;
	}

	public RPSEnum getPlayerChoice() {
		return playerChoice;
	}
	public void setOpponentChoice(RPSEnum choise) {
		this.opponentChoice = choise;
	}
	public void setPlayerChoice(RPSEnum choise) {
		this.playerChoice = choise;
	}
}
