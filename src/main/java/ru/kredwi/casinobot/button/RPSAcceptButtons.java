package ru.kredwi.casinobot.button;

import java.util.Random;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import ru.kredwi.casinobot.Bot;
import ru.kredwi.casinobot.command.CommandHandler;
import ru.kredwi.casinobot.embeds.rps.RPSResultEmbed;
import ru.kredwi.casinobot.embeds.rps.RPSSelectEmbed;
import ru.kredwi.casinobot.enums.RPSEnum;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.game.data.RPSData;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.games.RPS;
import ru.kredwi.casinobot.sql.JDBCActions;

public class RPSAcceptButtons {
	
	public Button[] getButtons() {
		
		
		return new Button[] { 
				ButtonHandler.getInstance().getButton("rps_paper_button"),
				ButtonHandler.getInstance().getButton("rps_scissors_button"),
				ButtonHandler.getInstance().getButton("rps_stone_button")
		};
	}
	
	public void execute(ButtonInteractionEvent event) {
		RPSData data = CommandHandler.getInstance().getGames(event.getHook().getId());
		String lang = JDBCActions.getUserLanguage(data.getPlayer());
		try {
			if (data.getPlayerChoice() == null) {
				data.setPlayerChoice(getEnumType(event.getButton().getId()));
				
				if (data.getOpponent() instanceof Bot) {
					JDBCActions.deleteUserBalance(data.getPlayer().getIdLong(), data.getDeposit());
					runGame(data, this.getRandomRPS(), lang, event);
					return; // stop next execute
				}
				RPSSelectEmbed embed = new RPSSelectEmbed(Pair.of(data.getOpponent(), data.getPlayer()), data.getDeposit(), lang);
				event.getHook().editOriginal("").setEmbeds(embed.build()).queue();
				
				return; // stop next execute
			}
			if (data.getOpponentChoice() == null) {
				JDBCActions.deleteUserBalance(data.getPlayer().getIdLong(), data.getDeposit());
				JDBCActions.deleteUserBalance(data.getOpponent().getIdLong(), data.getDeposit());
				runGame(data, getEnumType(event.getButton().getId()), lang, event);
			}
		} catch (GameNotFinished|LocaleKeyNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void runGame(RPSData data, RPSEnum opponentChoice, String lang, IDeferrableCallback callback) throws LocaleKeyNotFound {
		data.setOpponentChoice(opponentChoice);
		RPS game = new RPS(data.getPlayerChoice().name(), data.getOpponentChoice().name());
		
		try {
			RPSEnum[] choices = ((RPSEnum[]) game.getGameResult());

			Pair<User, RPSEnum> player = Pair.of(data.getPlayer(), choices[0]);
			Pair<User, RPSEnum> player2 = Pair.of(data.getOpponent(), choices[1]);
			
			if (choices[0].equals(choices[1])) {
				
				executeSendMessage(true, data, game, player, player2, lang, callback);
				
				JDBCActions.addUserBalance(data.getPlayer().getIdLong(), data.getDeposit());
				if (!(data.getOpponent() instanceof Bot)) {
					JDBCActions.addUserBalance(data.getOpponent().getIdLong(), data.getDeposit());
				}
				return; // stop next execute
			}
			
			if (game.isWin()) {
				changePlayerStats(game, data.getDeposit(), player.getLeft().getIdLong(), player2.getLeft().getIdLong());
				executeSendMessage(false, data, game, player, player2, lang, callback);
			} else {
				changePlayerStats(game, data.getDeposit(), player2.getLeft().getIdLong(), player.getLeft().getIdLong());
				executeSendMessage(false, data, game, player2, player, lang, callback);

			}
		} catch (GameNotFinished e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void executeSendMessage(boolean isDraw, RPSData data, Game game, Pair<User, RPSEnum> winner, Pair<User, RPSEnum> loser, String lang, IDeferrableCallback callback) throws GameNotFinished, LocaleKeyNotFound {
		RPSResultEmbed embed = new RPSResultEmbed(isDraw, game, winner, loser, data.getDeposit(), lang);
		callback.getHook().editOriginal("").setEmbeds(embed.build()).setComponents().queue();		
	}
	
	private RPSEnum getEnumType(String id) {
		switch (id) {
			case "rps_paper_button": {
				return RPSEnum.PAPER;
			}
			case "rps_scissors_button": {
				return RPSEnum.SCISSORS;
			}
			case "rps_stone_button": {
				return RPSEnum.ROCK;
			} default: return null;
		}
	}
	
	private RPSEnum getRandomRPS() {
		return RPSEnum.values()[new Random().nextInt(RPSEnum.values().length)];
	}
	
	private void changePlayerStats(Game game, double deposit, long winner, long loser) throws GameNotFinished {
		JDBCActions.addUserWin(winner, 1);
		JDBCActions.addUserBalance(winner, game.getPrize(deposit) + deposit);
		JDBCActions.addUserLose(loser, 1);
	}

}
