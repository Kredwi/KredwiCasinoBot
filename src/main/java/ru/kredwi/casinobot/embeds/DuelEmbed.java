package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class DuelEmbed extends EmbedBuilder implements IGameEmbed {
	public DuelEmbed(IGames game, User user, User opponent, double deposit, String lang) throws GameNotFinished, LocaleKeyNotFound {
		
		int[] gameResult = (int[]) game.getGameResult();
		
		setCustomFooter(this, game.getGameUUID(), lang);
		
		setDescription(String.format(
				MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_DUEL_RESULT),
				formatInt(gameResult[0]), formatInt(gameResult[1])
				));
		
		addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_DEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
		if (game.isWin()) {
			addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_PRIZE), String.format(DEPOSIT_SHOW_PATTERN, game.getPrize(deposit)), true);
			getExampleWin(this, lang);
		} else if (gameResult[0] == gameResult[1]) {
			addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_RETURNDEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
			getExampleDraw(this, lang);
		} else {
			getExampleLose(this, lang);
		}
	}
	private final String formatInt(int i) {
		return MarkdownUtil.bold(String.valueOf(i));
	}
}