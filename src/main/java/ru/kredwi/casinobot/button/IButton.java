package ru.kredwi.casinobot.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;

public interface IButton {
	
	public Button getData();
	
	public Button getLozalizatedButton(String lang) throws LocaleKeyNotFound;
	
	public void execute(ButtonInteractionEvent event);
}
