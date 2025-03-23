package ru.kredwi.casinobot.command.slash;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.alerts.AlertEmbed;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;
import ru.kredwi.casinobot.sql.JDBCActions;

public class BalanceCMD implements ISlashCommand {
	
	@Override
	public SlashCommandData getData() {
		return Commands.slash(BALANCE_COMMAND, "Check your balance");
	}

	@Override
	public void execute(@NotNull SlashCommandInteractionEvent commandEvent, String lang) {
		try {
			User user = commandEvent.getUser();
			
			final float balance = JDBCActions.getUserBalance(user);
			
			String title = MainLocale.getResource(lang, LocaleMessagesKeys.BOT_COMMANDS_GENERAL_ALERT);
			String description = MainLocale.getResource(lang, LocaleMessagesKeys.BOT_COMMANDS_BALANCE);
				
			EmbedBuilder rewardEmbed = new AlertEmbed(title, String.format(description, balance));
			
			commandEvent.getHook().editOriginal("").setEmbeds(rewardEmbed.build()).queue();
		} catch (LocaleKeyNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}