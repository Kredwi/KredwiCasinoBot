package ru.kredwi.casinobot.games;

import java.util.UUID;

import ru.kredwi.casinobot.enums.CoinSide;
import ru.kredwi.casinobot.exception.GameNotFinished;

public class Coin implements IGames {
	
	private final UUID gameUUID = UUID.randomUUID();
	private final short botSide = (short) RANDOM.nextInt(CoinSide.values().length);
	
	private final CoinSide[] gameResult = new CoinSide[2];
	
	private boolean win;
	private boolean finish;
	
	public Coin(String playerSide) {
		gameResult[0] = CoinSide.valueOf(playerSide.toUpperCase());
		gameResult[1] = CoinSide.values()[botSide];
		
		if (gameResult[0].equals(gameResult[1])) {
			this.win = true;
		}
		
		this.finish = true;
	}
	
	@Override
	public CoinSide[] getGameResult() throws GameNotFinished {
		throwIfNotFinish(finish);
		return gameResult;
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
	public String getIcon(int i) {
		CoinSide[] values = CoinSide.values();
		if (i < 0 || i >= values.length)
			throw new IndexOutOfBoundsException("Invalid entity index: " + i);
		return values[i].toString();
	}

	@Override
	public double getPrize(double deposit) throws GameNotFinished {
		return deposit * 1.194;
	}

}
