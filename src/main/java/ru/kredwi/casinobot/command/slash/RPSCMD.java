package ru.kredwi.casinobot.command.slash;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import ru.kredwi.casinobot.Bot;
import ru.kredwi.casinobot.button.RPSAcceptButtons;
import ru.kredwi.casinobot.command.CommandHandler;
import ru.kredwi.casinobot.command.IErrorCommand;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.rps.RPSSelectEmbed;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.game.data.RPSData;
import ru.kredwi.casinobot.sql.JDBCActions;

public class RPSCMD implements ISlashCommand, IErrorCommand {
	
	private final Bot botInstance = new Bot();
	
	@Override
	public SlashCommandData getData() {
		OptionData option = new OptionData(OptionType.STRING, "choice", "Select your choice!", false)
                .addChoice("ROCK", "ROCK")
                .addChoice("PAPER", "PAPER")
                .addChoice("SCISSORS", "SCISSORS");
		return Commands.slash(RPS_GAME_COMMAND, "Play this happy game :)")
				.addOption(OptionType.NUMBER, "deposit", "Write your deposit!", true)
//				.addOptions(option)
				.addOption(OptionType.USER, "opponent", "Select your opponent!");
	}

	@Override
	public void execute(SlashCommandInteractionEvent commandEvent, String lang) {
		
		OptionMapping opponentOption = commandEvent.getOption("opponent");
		double deposit = commandEvent.getOption("deposit").getAsDouble();
		final User opponent = opponentOption != null ? opponentOption.getAsUser() : botInstance;
		final User user = commandEvent.getUser();
		
		try {
			
			if (!isPlayersHaveNeedMoney(user, opponent, deposit)) {
				commandEvent.getHook().editOriginal("").setEmbeds(noMoneyGetEmbed(deposit, lang)).queue();
				return;
			}
			
			RPSAcceptButtons buttons = new RPSAcceptButtons();
			
			String gameID = commandEvent.getHook().getId();
			
			RPSData data = new RPSData(gameID, user, opponent, deposit);
			
			CommandHandler.getInstance().setGames(gameID, data);
			
			RPSSelectEmbed embed = new RPSSelectEmbed(Pair.of(user, opponent), deposit, lang);
			
			commandEvent.getHook().editOriginal("").setEmbeds(embed.build()).setActionRow(buttons.getButtons()).queue();	
		} catch (GameNotFinished|LocaleKeyNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean isPlayersHaveNeedMoney(User playerOne, User playerTwo, double deposit) {
		if (playerTwo == null || playerTwo instanceof Bot) {
			return JDBCActions.getUserBalance(playerOne) >= deposit;
		}
		return JDBCActions.getUserBalance(playerOne) >= deposit & JDBCActions.getUserBalance(playerTwo) >= deposit;
	}
}
