package ru.kredwi.casinobot.games;

import java.util.Random;
import java.util.UUID;

import ru.kredwi.casinobot.exception.GameNotFinished;

public abstract class Game {
	
	protected static final Random RANDOM = new Random();
	
	/**
	 * @author Kredwi
	 * @param is finish?
	 * @return void
	 * */
	public void throwIfNotFinish(boolean finish) throws GameNotFinished {
		if (!finish) {
			throw new GameNotFinished(String.format("Game %s not finished!!", getGameUUID()));
		}
	}
	
	/**
	 * @author Kredwi
	 * @throws GameNotFinished
	 * @return Any object in game result
	 * */
	public Object getGameResult() throws GameNotFinished {
		return new int[0][0];
	};
	
	/**
	 * @author Kredwi
	 * @throws GameNotFinished
	 * @return state game
	 * */
	public abstract boolean isWin() throws GameNotFinished;
	
	/**
	 * @author Kredwi
	 * @throws GameNotFinished
	 * @return Sum of win streak
	 * */
	public int getWinCount() throws GameNotFinished {
		return 0;
	};
	
	/**
	 * @author Kredwi
	 * @return UUID of a game
	 * */
	public abstract UUID getGameUUID();
	
	/**
	 * @author Kredwi
	 * @param i Index of emoji
	 * @return Emoji messages
	 * */
	public String getIcon(int i) {
		return "";
	};
	
	/**
	 * @author Kredwi
	 * @param deposit is deposit
	 * @return number of prize
	 * */
	public abstract double getPrize(double deposit) throws GameNotFinished;
}