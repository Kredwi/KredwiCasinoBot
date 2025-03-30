package ru.kredwi.casinobot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.kredwi.casinobot.CasinoBot;

public class MessageReceivedListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentStripped();
		if (!message.startsWith(CasinoBot.config.getProperty("prefix")) | event.getMember().getUser().isBot()) {
			
		} else {
			// i not understand its life
		}
	}
}