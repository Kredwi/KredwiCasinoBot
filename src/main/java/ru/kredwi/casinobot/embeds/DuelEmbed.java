package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.entities.User;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;

public class DuelEmbed extends GameEmbedBuilder {
	public DuelEmbed(Game game, User user, User opponent, double deposit, String lang) throws GameNotFinished, LocaleKeyNotFound {
		
		int[] gameResult = (int[]) game.getGameResult();
		
		setCustomFooter(game.getGameUUID(), lang);
		
		setDescription(String.format(
				getLocalText(lang, LocaleMessagesKeys.GAMES_DUEL_RESULT),
				formatToBold(gameResult[0]), formatToBold(gameResult[1])
				));
		
		if (gameResult[0] == gameResult[1]) {
			addField(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_RETURNDEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
			setDraw(lang);
			return;
		}
		
		setMathResult(game, deposit, lang);
	}
}