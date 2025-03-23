package ru.kredwi.casinobot.command.slash;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import ru.kredwi.casinobot.command.IErrorCommand;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.DuelEmbed;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Duel;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.sql.JDBCActions;

public class DuelCMD implements ISlashCommand, IErrorCommand {
	@Override
	public SlashCommandData getData() {
		return Commands.slash(DUEL_GAME_COMMAND, "Play this happy game :)")
				.addOption(OptionType.USER, "opponent", "Select your opponent!", true)
				.addOption(OptionType.NUMBER, "deposit", "Write your deposit!", true);
	}

	@Override
	public void execute(SlashCommandInteractionEvent commandEvent, String lang) {
		
		final User user = commandEvent.getUser();
		double deposit = commandEvent.getOption("deposit").getAsDouble();
		
		User opponent = commandEvent.getOption("opponent").getAsUser();
		User username = commandEvent.getUser();
		
		if (JDBCActions.getUserBalance(user) < deposit) {

			commandEvent.getHook().editOriginal("").setEmbeds(noMoneyGetEmbed(deposit, lang)).queue();
			return;
		} else JDBCActions.deleteUserBalance(user.getIdLong(), deposit);
		
		IGames game = new Duel();

		try {
			if (game.isWin()) {
				JDBCActions.addUserWin(user.getIdLong(), 1);
				JDBCActions.addUserBalance(user.getIdLong(), game.getPrize(deposit) + deposit);
			} else {
				JDBCActions.addUserLose(user.getIdLong(), 1);
			}
			MessageEmbed embed = new DuelEmbed(game, username, opponent, deposit, lang).build();
			
			commandEvent.getHook().editOriginal("").setEmbeds(embed).queue();
		} catch (GameNotFinished | LocaleKeyNotFound e) {
			exception(commandEvent, e.getMessage(), lang);
		}
	}
}
