package ru.kredwi.casinobot.command.slash;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.alerts.AlertEmbed;
import ru.kredwi.casinobot.embeds.alerts.ErrorEmbed;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;
import ru.kredwi.casinobot.sql.JDBCActions;

public class LanguageCMD implements ISlashCommand {
	
	private static final String languageCodeKey = "language_code";
	
	@Override
	public SlashCommandData getData() {
		
		OptionData option = new OptionData(OptionType.STRING, languageCodeKey, "select", true)
				// Values from Discord Locales
                .addChoice("English", "en-US");
		
		return Commands.slash(LANGUAGE_COMMAND, "Change bot language")
				.addOptions(option);
	}

	@Override
	public void execute(@NotNull SlashCommandInteractionEvent commandEvent, String lang) {
		try {
			
			EmbedBuilder embed;
			
			String code = commandEvent.getOption(languageCodeKey).getAsString();
			
			User user = commandEvent.getUser();
			
			DiscordLocale locale = DiscordLocale.from(code);
			
			if (locale != DiscordLocale.UNKNOWN) {
				String localeKey = locale.getLocale();
				JDBCActions.setUserLanguage(user, localeKey);
				
				String title = MainLocale.getResource(localeKey, LocaleMessagesKeys.BOT_COMMANDS_LANGUAGE_SUCCESS_TITLE);
				String description = MainLocale.getResource(localeKey, LocaleMessagesKeys.BOT_COMMANDS_LANGUAGE_SUCCESS_DESCRIPTION);
				
				embed = new AlertEmbed(title, String.format(description, localeKey));	
			} else {
				
				String localeKey = JDBCActions.getUserLanguage(user);
				String description = MainLocale.getResource(localeKey, LocaleMessagesKeys.BOT_COMMANDS_LANGUAGE_ERROR_ACCESS);
				
				embed = new ErrorEmbed(description);
			}
			commandEvent.getHook().editOriginal("").setEmbeds(embed.build()).queue();
		} catch (LocaleKeyNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}