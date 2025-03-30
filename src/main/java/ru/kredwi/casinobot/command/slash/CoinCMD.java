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
import ru.kredwi.casinobot.embeds.CoinEmbed;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Coin;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.sql.JDBCActions;

public class CoinCMD implements ISlashCommand, IErrorCommand {
	
	@Override
	public SlashCommandData getData() {
		OptionData option = new OptionData(OptionType.STRING, "side", "Select your side!", true)
                .addChoice("HEADS", "HEADS")
                .addChoice("TAILS", "TAILS");
		
		return Commands.slash(COIN_GAME_COMMAND, "Play this happy game :)")
				.addOption(OptionType.NUMBER, "deposit", "Write your deposit!", true)
				.addOptions(option);
	}

	@Override
	public void execute(@NotNull SlashCommandInteractionEvent commandEvent, String lang) {
		try {
			User user = commandEvent.getUser();
			String side = commandEvent.getOption("side").getAsString();
			double deposit = commandEvent.getOption("deposit").getAsDouble();
			
			if (JDBCActions.getUserBalance(user) < deposit) {
				commandEvent.getHook().editOriginal("").setEmbeds(noMoneyGetEmbed(deposit, lang)).queue();
				return;
			} else JDBCActions.deleteUserBalance(user.getIdLong(), deposit);
			
			Game coin = new Coin(side);
			
			if (coin.isWin()) {
				JDBCActions.addUserWin(user.getIdLong(), 1);
				JDBCActions.addUserBalance(user.getIdLong(), coin.getPrize(deposit) + deposit);
			} else {
				JDBCActions.addUserLose(user.getIdLong(), 1);
			}
			MessageEmbed embed = new CoinEmbed(coin, deposit, commandEvent).build();
			
			commandEvent.getHook().editOriginal("").setEmbeds(embed).queue();
		} catch (GameNotFinished | LocaleKeyNotFound e) {
			exception(commandEvent, e.getMessage(), lang);
		}
	}

}