package ru.kredwi.casinobot.games;

import java.util.UUID;

import ru.kredwi.casinobot.exception.GameNotFinished;

public class Duel implements IGames {
	
	private final UUID gameUUID = UUID.randomUUID();
	private final int userPeak = RANDOM.nextInt(2);
	private final int apponentPeak = RANDOM.nextInt(2);
	
	private int[] gameBoard = new int[2];
	private boolean win;
	private boolean finish;
	
	public Duel() {
		gameBoard[0] = userPeak;
		gameBoard[1] = apponentPeak;
		
		if (userPeak > apponentPeak) {
			this.win = true;
		}
		this.finish = true;
	}
	
	@Override
	public Object getGameResult() throws GameNotFinished {
		throwIfNotFinish(finish);
		return gameBoard;
	};
	
	@Override
	public boolean isWin() throws GameNotFinished {
		throwIfNotFinish(finish);
		return win;
	}

	@Override
	public UUID getGameUUID() {
		return gameUUID;
	}

	@Override
	public double getPrize(double deposit) throws GameNotFinished {
		return deposit * 1.6;
	}

}