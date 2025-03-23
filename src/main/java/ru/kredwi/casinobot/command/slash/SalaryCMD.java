package ru.kredwi.casinobot.command.slash;

import java.sql.Timestamp;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.alerts.AlertEmbed;
import ru.kredwi.casinobot.embeds.alerts.ErrorEmbed;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;
import ru.kredwi.casinobot.sql.JDBCActions;

public class SalaryCMD implements ISlashCommand {
	
	private static final int DAY_OF_MS = 86_400_000;
	private static final int SALARY = 2_123;
	
	
	@Override
	public SlashCommandData getData() {
		return Commands.slash(SALARY_COMMAND, "Get your salary");
	}

	@Override
	public void execute(@NotNull SlashCommandInteractionEvent commandEvent, String lang) {
		try {
			User user = commandEvent.getUser();
			
			Timestamp date = JDBCActions.getUserDateSalary(user);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			
			if (now.after(date)) {
				JDBCActions.addUserBalance(user.getIdLong(), SALARY);
				JDBCActions.setUserDateSalary(user, new Timestamp(now.getTime() + DAY_OF_MS));
				
				String title = MainLocale.getResource(lang, LocaleMessagesKeys.BOT_COMMANDS_GENERAL_ALERT);
				String description = MainLocale.getResource(lang, LocaleMessagesKeys.SALARY_RECEIVED);
				
				EmbedBuilder rewardEmbed = new AlertEmbed(title, String.format(description, SALARY));
				
				commandEvent.getHook().editOriginal("").setEmbeds(rewardEmbed.build()).queue();
			} else {	
				String title = MainLocale.getResource(lang, LocaleMessagesKeys.SALARY_ALREADY_RECEIVED_TITLE);
				String description = MainLocale.getResource(lang, LocaleMessagesKeys.SALARY_ALREADY_RECEIVED_DESCRIPTION);
				
				long nextSalary = (date.getTime() - now.getTime()) / 1000;
				
				EmbedBuilder errorEmbed = new ErrorEmbed(title, String.format(description, nextSalary));
				
				commandEvent.getHook().editOriginal("").setEmbeds(errorEmbed.build()).queue();
			}
		} catch (LocaleKeyNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
