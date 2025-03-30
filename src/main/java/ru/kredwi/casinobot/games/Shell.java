package ru.kredwi.casinobot.games;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import ru.kredwi.casinobot.exception.GameNotFinished;

public class Shell extends Game {

	private static final Map<UUID, Game> GAMES = new HashMap<>();
	
	private final UUID gameUUID = UUID.randomUUID();
	private final int gameNumber = RANDOM.nextInt(3);
	
	private boolean[] gameResult = new boolean[3];
	
	
	public Shell() {
		gameResult[gameNumber] = true;
		GAMES.put(gameUUID, this);
	}
	
	@Override
	public boolean isWin() throws GameNotFinished {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID getGameUUID() {
		return gameUUID;
	}

	@Override
	public String getIcon(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Game getShellGame(@NotNull UUID id) {
		return GAMES.get(id);
	}

	@Override
	public double getPrize(double deposit) throws GameNotFinished {
		// IDK
		return deposit;
	}

}
