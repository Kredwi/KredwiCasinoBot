package ru.kredwi.casinobot.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public interface IButton {
	
	public Button getData();
	
	public void execute(ButtonInteractionEvent event);
}
