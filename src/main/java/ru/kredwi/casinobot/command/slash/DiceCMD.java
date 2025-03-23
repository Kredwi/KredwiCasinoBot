package ru.kredwi.casinobot.command.slash;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import ru.kredwi.casinobot.command.IErrorCommand;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.DiceEmbed;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Dice;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.sql.JDBCActions;

public class DiceCMD implements ISlashCommand, IErrorCommand  {

	@Override
	public SlashCommandData getData() {
		OptionData option = new OptionData(OptionType.INTEGER, "side", "Select your side!", true)
                .addChoice("1", 1)
                .addChoice("2", 2)
                .addChoice("3", 3)
                .addChoice("4", 4)
                .addChoice("5", 5)
                .addChoice("6", 6);
		return Commands.slash(DICE_GAME_COMMAND, "Play this happy game :)")
				.addOption(OptionType.NUMBER, "deposit", "Write your deposit!", true)
				.addOptions(option);
	}

	@Override
	public void execute(@NotNull SlashCommandInteractionEvent commandEvent, String lang) {
		final User user = commandEvent.getUser();
		int side = commandEvent.getOption("side").getAsInt();
		double deposit = commandEvent.getOption("deposit").getAsDouble();
		
		if (JDBCActions.getUserBalance(user) < deposit) {
			commandEvent.getHook().editOriginal("").setEmbeds(noMoneyGetEmbed(deposit, lang)).queue();
			return;
		} else JDBCActions.deleteUserBalance(user.getIdLong(), deposit);
		
		IGames game = new Dice((short)side);

		try {
			if (game.isWin()) {
				JDBCActions.addUserWin(user.getIdLong(), 1);
				JDBCActions.addUserBalance(user.getIdLong(), game.getPrize(deposit) + deposit);
			} else {
				JDBCActions.addUserLose(user.getIdLong(), 1);
			}
			MessageEmbed embed = new DiceEmbed(game, deposit, commandEvent).build();
			commandEvent.getHook().editOriginal("").setEmbeds(embed).queue();
		} catch (GameNotFinished | LocaleKeyNotFound e) {
			exception(commandEvent, e.getMessage(), lang);
		}
	}
}
