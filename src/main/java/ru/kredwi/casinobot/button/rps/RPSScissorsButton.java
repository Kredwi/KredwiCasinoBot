package ru.kredwi.casinobot.button.rps;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import ru.kredwi.casinobot.button.IButton;
import ru.kredwi.casinobot.button.RPSAcceptButtons;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class RPSScissorsButton extends RPSAcceptButtons implements IButton {
	
	private static final String BUTTON_ID = "rps_scissors_button";
	
	@Override
	public Button getData() {
		return Button.primary(BUTTON_ID, "Scissors");
	}

	@Override
	public Button getLozalizatedButton(String lang) throws LocaleKeyNotFound {
		String buttonName = MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_RSP_BUTTON_SCISSORS);
		if (buttonName == null) {
			return this.getData();
		} else {
			return Button.primary(BUTTON_ID, buttonName);	
		}
	}
	
}
