package ru.kredwi.casinobot.command.slash;

import java.text.MessageFormat;
import java.util.List;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import ru.kredwi.casinobot.command.IErrorCommand;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.alerts.AlertEmbed;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;
import ru.kredwi.casinobot.sql.DBUser;
import ru.kredwi.casinobot.sql.JDBCActions;

public class TopCMD implements ISlashCommand, IErrorCommand {

	@Override
	public SlashCommandData getData() {
		return Commands.slash(TOP_COMMAND, "Show top players!");
	}

	@Override
	public void execute(SlashCommandInteractionEvent commandEvent, String lang) {
		try {
			List<DBUser> users = JDBCActions.getWithTopBalance();
			
			StringBuilder builder = new StringBuilder();
			
			for (DBUser user : users) {
				String format = "{0} ({1}) {2} {3}/{4}";
				builder.append(MessageFormat.format(format, user.getUsername(), user.getDiscordId(), user.getBalance(), user.getWin(), user.getLose()));
				builder.append("\n");
			}
			
			String title = MainLocale.getResource(lang, LocaleMessagesKeys.BOT_COMMANDS_TOP_TITLE);
			
			AlertEmbed embed = new AlertEmbed(title, builder.toString().trim());
			
			commandEvent.getHook().editOriginal("").setEmbeds(embed.build()).queue();
		} catch (LocaleKeyNotFound e) {
			exception(commandEvent, "Locale key is not found", lang);
		}
	}
	
}