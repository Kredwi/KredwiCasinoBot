package ru.kredwi.casinobot.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.kredwi.casinobot.command.CommandHandler;

public class SlashCommandListener extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if (!event.getUser().isBot()) {
			CommandHandler.getInstance().getAndExecuteCommand(event);
		}
	}
	
}