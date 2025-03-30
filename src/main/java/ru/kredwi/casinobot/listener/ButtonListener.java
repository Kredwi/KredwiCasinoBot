package ru.kredwi.casinobot.listener;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.kredwi.casinobot.button.ButtonHandler;

public class ButtonListener extends ListenerAdapter {

	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		event.deferEdit().queue();
		ButtonHandler.getInstance().executeButton(event);
	}
	
}
