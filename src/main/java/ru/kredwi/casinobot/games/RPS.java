package ru.kredwi.casinobot.games;

import java.util.UUID;

import ru.kredwi.casinobot.enums.RPSEnum;
import ru.kredwi.casinobot.exception.GameNotFinished;

public class RPS extends Game {

	private static final String[] ICONS = new String[] {
			"🪨", "✂️", "🧻"
	};
	
	private final UUID gameUUID = UUID.randomUUID();
	
	private RPSEnum[] gameResult = new RPSEnum[2];
	private boolean win;
	
	public RPS(String playerChoice, String opponentChoice) {
		gameResult[0] = RPSEnum.valueOf(playerChoice); // is player
		gameResult[1] = RPSEnum.valueOf(opponentChoice); // is opponent
		if (isKill(gameResult[0], gameResult[1])) {
			this.win = true;
		}
	}
	
	private final boolean isKill(RPSEnum player, RPSEnum opponent) {
		if (player.equals(RPSEnum.ROCK) & opponent.equals(RPSEnum.SCISSORS)) {
			return true;
		} else if (player.equals(RPSEnum.SCISSORS) & opponent.equals(RPSEnum.PAPER)) {
			return true;
		} else if (player.equals(RPSEnum.PAPER) & opponent.equals(RPSEnum.ROCK)) {
			return true;
		}
		return false;
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
		if (i < 0 | i > ICONS.length)
			throw new IndexOutOfBoundsException("Invalid entity index: " + i);
		return ICONS[i];
	}

	@Override
	public double getPrize(double deposit) throws GameNotFinished {
		return deposit * 1.394;
	}

}