package ru.kredwi.casinobot.exception;

public class GameNotFinished extends Throwable {

	private static final long serialVersionUID = 1L;
	
	public GameNotFinished() { super(); };
	public GameNotFinished(String message) { super(message); };
	
}