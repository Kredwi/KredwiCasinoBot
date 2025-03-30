package ru.kredwi.casinobot.button.rps;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import ru.kredwi.casinobot.button.IButton;
import ru.kredwi.casinobot.button.RPSAcceptButtons;

public class RPSStoneButton extends RPSAcceptButtons implements IButton {

	@Override
	public Button getData() {
		return Button.primary("rps_stone_button", "Stone");
	}
}
