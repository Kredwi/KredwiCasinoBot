package ru.kredwi.casinobot.command;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.CasinoBot;
import ru.kredwi.casinobot.embeds.alerts.ErrorEmbed;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public interface IErrorCommand {
	default MessageEmbed noMoneyGetEmbed(Object deposit, String lang) {
		try {
			String title = MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_ERROR_NOMONEY_TITLE);
			String description = MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_ERROR_NOMONEY_DESCRIPTION);
			return new ErrorEmbed(title, String.format(description, deposit)).build();
		} catch (LocaleKeyNotFound e) {
			return new ErrorEmbed(e.getMessage()).build();
		}
	}
	default void exception(SlashCommandInteractionEvent commandEvent, String message, String lang) {
		MessageEmbed embed = new ErrorEmbed("Locale error", "So very bad....").build();
		
		try {
			CasinoBot.getLogger().error(message);
			String nmessage = MainLocale.getResource(commandEvent.getGuild().getLocale().toString(), LocaleMessagesKeys.BOT_COMMANDS_GENERAL_ERROR);
			
			embed = new ErrorEmbed(String.format(nmessage, message)).build();
		} catch (LocaleKeyNotFound e) {
			e.printStackTrace();
		}
		commandEvent.getHook().editOriginal("").setEmbeds(embed).queue();
	}
}