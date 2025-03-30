package ru.kredwi.casinobot.games;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import ru.kredwi.casinobot.exception.GameNotFinished;

public class Slot extends Game {
	
	private static final int[] DEFAULT_BOARD_SIZE = new int[] {3, 3};
	private static final int[] MAX_BOARD_SIZE = new int[] {10, 10};
	private static final String[] ICONS = new String[] {
			"💀", "🤷", "🧱", "👍", "👎", "🧐", "✅", "☠️", "👌",
	};
	
	private final int[][] gameBoard;
	private final UUID gameUUID = UUID.randomUUID();
	
	private int winSum;
	private boolean win;
	private boolean finish;
	
	public Slot() { this(DEFAULT_BOARD_SIZE[0], DEFAULT_BOARD_SIZE[1]); };
	
	public Slot(int row) { this(DEFAULT_BOARD_SIZE[0], row); };
	
	public Slot(@NotNull int column, @NotNull int row) {
		if (column <= 0 | row <= 0)
			throw new IllegalArgumentException("Columns and rows must be positive integers.");
		else if (column > MAX_BOARD_SIZE[0] | row > MAX_BOARD_SIZE[1])
			throw new IndexOutOfBoundsException(String.format(
					"Columns or rows IS BIG! Columns Max: %s Rows Max: %s",
					MAX_BOARD_SIZE[0], MAX_BOARD_SIZE[1]));
		
		this.gameBoard = new int[column][row];
		this.startGame();
	};
	
	
	private void startGame() {
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				gameBoard[i][j] = RANDOM.nextInt(ICONS.length);
			}
		}
		int winCount = this.getWinCondition();
		if (winCount > 0) {
			this.win = true;
			this.winSum = winCount;
		}
		
		this.finish = true;
	}
	
	private int getWinCondition() {
		int winCount = 0;
		
		for (int i = 0; i < gameBoard.length; i++) {
			if (this.checkLine(gameBoard[i])) {
				winCount++;
			}
		}
		for (int i = 0; i < gameBoard[0].length; i++) {
			int[] column = new int[gameBoard.length];
			for (int row = 0; row < gameBoard.length; row++) {
				column[row] = gameBoard[row][i];
			}
			if (this.checkLine(column)) winCount++;
		}
		return winCount;
	}
	
	private boolean checkLine(int[] line) {
		int count = 1;
		int last = -1;
		for (int j : line) {
			if (last == j & last != -1) {
				count++;
			} else {
				count = 1;
			}
			last = j;
		}
		return count >= line.length;
	}
	
	@Override
	public int[][] getGameResult() throws GameNotFinished {
		throwIfNotFinish(finish);
		return gameBoard;
	};
	
	@Override
	public boolean isWin() throws GameNotFinished {
		throwIfNotFinish(finish);
		return win;
	}
	
	@Override
	public int getWinCount() throws GameNotFinished {
		throwIfNotFinish(finish);
		return winSum;
	}

	@Override
	public UUID getGameUUID() {
		return gameUUID;
	}
	
	@Override
	public String toString() {
		return gameUUID.toString();
	}

	@Override
	public String getIcon(int i) {
		if (i < 0 || i >= ICONS.length)
			throw new IndexOutOfBoundsException("Invalid entity index: " + i);
		return ICONS[i];
	}

	@Override
	public double getPrize(double deposit) throws GameNotFinished {
		return deposit * (1.7 + 0.1 * (this.getWinCount() - 1));
	}
}