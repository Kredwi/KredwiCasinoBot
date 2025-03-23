package ru.kredwi.casinobot.games;

import java.util.UUID;

import ru.kredwi.casinobot.exception.GameNotFinished;

public class Dice implements IGames {

	private static final String[] ICONS = new String[] {
			"⬜️", "⬛️"
	};
	private static final int[][] COMBINATIONS = new int[][] {
        {0,0,0, 0,1,0, 0,0,0}, // 1
        {1,0,0, 0,0,0, 0,0,1}, // 2
        {1,0,0, 0,1,0, 0,0,1}, // 3
        {1,0,1, 0,0,0, 1,0,1}, // 4
        {1,0,1, 0,1,0, 1,0,1}, // 5
        {1,0,1, 1,0,1, 1,0,1}  // 6
	};
	
	private final UUID gameUUID = UUID.randomUUID();
	private final short dropped = (short) (RANDOM.nextInt((COMBINATIONS.length - 1)) + 1);
	private final int[] gameResult = new int[2];
	
	private boolean win;
	
	public Dice(short choice) {
		this.gameResult[0] = choice;
		this.gameResult[1] = dropped;
		if (gameResult[0] == gameResult[1]) this.win = true;
	}
	
	@Override
	public Object getGameResult() throws GameNotFinished {
		return gameResult;
	}
	
	@Override
	public boolean isWin() throws GameNotFinished {
		return win;
	}

	@Override
	public UUID getGameUUID() {
		return gameUUID;
	}

	@Override
	public String getIcon(int i) {
		
		i--;
		
		if (i < 0 | i > COMBINATIONS.length)
			throw new IndexOutOfBoundsException("Index Out Of Bounds in Dice Icons!");
		
		StringBuilder builder = new StringBuilder();
		
		for (int row = 0; row < COMBINATIONS[i].length; row++) {
			if (row % 3 == 0) builder.append("\n");
			builder.append(ICONS[COMBINATIONS[i][row]]);
			
		}
		return builder.toString().trim();
	}

	@Override
	public double getPrize(double deposit) throws GameNotFinished {
		return deposit * 1.6;
	}

}
