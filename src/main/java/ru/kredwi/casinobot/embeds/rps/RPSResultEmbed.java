package ru.kredwi.casinobot.embeds.rps;

import java.awt.Color;
import java.time.Instant;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import ru.kredwi.casinobot.embeds.GameEmbedBuilder;
import ru.kredwi.casinobot.enums.RPSEnum;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;

public class RPSResultEmbed extends GameEmbedBuilder {
	public RPSResultEmbed(boolean isDraw, Game game, Pair<User, RPSEnum> winner, Pair<User, RPSEnum> loser, double deposit, String lang) throws GameNotFinished, LocaleKeyNotFound {
		if (!isDraw) {
			setWinner(game, winner, loser, lang);
		} else {
			setDraw(game, winner, loser, lang);
		}
		
		setTimestamp(Instant.now());
		addField("deposit", String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
	}
	private void setWinner(Game game, Pair<User, RPSEnum> winner, Pair<User, RPSEnum> loser, String lang) throws LocaleKeyNotFound {
		setTitle(String.format(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_DOUBLE_WIN), winner.getLeft().getName()));
		setDescription(getFormatedMessage(game, winner, loser, lang));
		setColor(Color.yellow);
	}
	
	private void setDraw(Game game, Pair<User, RPSEnum> player1, Pair<User, RPSEnum> player2, String lang) throws LocaleKeyNotFound {
		setTitle(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_DOUBLE_WIN));
		setDescription(getFormatedMessage(game, player1, player2, lang));
		setColor(Color.CYAN);
	}
	
	private String getFormatedMessage(Game game, Pair<User, RPSEnum> player1, Pair<User, RPSEnum> player2, String lang) throws LocaleKeyNotFound {
		String syntax = getLocalText(lang, LocaleMessagesKeys.GAMES_RSP_SYNTAX);
		return String.format(syntax, player1.getLeft().getName(), game.getIcon(player1.getRight().ordinal()), player2.getLeft().getName(), game.getIcon(player2.getRight().ordinal()));
	}
}
