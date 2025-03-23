package ru.kredwi.casinobot.locale;

public enum LocaleMessagesKeys {
	BOT_COMMANDS_COIN_DESCRIPTION,
	BOT_COMMANDS_TOP_TITLE,
	
	BOT_COMMANDS_LANGUAGE_SUCCESS_TITLE,
	BOT_COMMANDS_LANGUAGE_SUCCESS_DESCRIPTION,
	BOT_COMMANDS_LANGUAGE_ERROR_ACCESS,
	BOT_COMMANDS_LANGUAGE_ERROR_SUPPORT,
	
	BOT_COMMANDS_BALANCE,
	BOT_COMMANDS_GENERAL_WAITING,
	BOT_COMMANDS_GENERAL_ERROR,
	BOT_COMMANDS_GENERAL_ALERT,
	
	SALARY_RECEIVED,
	SALARY_ALREADY_RECEIVED_TITLE,
	SALARY_ALREADY_RECEIVED_DESCRIPTION,
	
	GAMES_GENERAL_WIN,
	GAMES_GENERAL_DRAW,
	GAMES_GENERAL_LOSE,
	GAMES_GENERAL_PRIZE,
	GAMES_GENERAL_ID,
	GAMES_GENERAL_DEPOSIT,
	GAMES_GENERAL_RETURNDEPOSIT,
	GAMES_GENERAL_STREAK,
	GAMES_GENERAL_ERROR_NOMONEY_TITLE,
	GAMES_GENERAL_ERROR_NOMONEY_DESCRIPTION,
	
	GAMES_COIN_TAILS,
	GAMES_COIN_HEADS,
	GAMES_COIN_RESULT,
	
	GAMES_DICE_DROPPED,

	GAMES_RSP_CHOICE,
	GAMES_RSP_OPCHOICE,
	
	GAMES_DUEL_RESULT;
	
	public String getPath() {
		return this.name().toLowerCase().replaceAll("_", ".");
	}
}