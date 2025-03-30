package ru.kredwi.casinobot.embeds;

import java.awt.Color;
import java.util.MissingFormatArgumentException;
import java.util.UUID;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;

import static ru.kredwi.casinobot.locale.MainLocale.getResource;
import static ru.kredwi.casinobot.CasinoBot.getLogger;

public class GameEmbedBuilder extends EmbedBuilder {
	
	protected static final Color DEFAULT_EMBED_COLOR = Color.CYAN;
	protected static final Color WIN_COLOR = Color.YELLOW;
	protected static final Color LOSE_COLOR = Color.RED;
	protected static final String DEPOSIT_SHOW_PATTERN = "%.3f";
	
	protected void setWin(String lang) throws LocaleKeyNotFound {
		setTitle(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_WIN));
		setColor(WIN_COLOR);
	}			
	protected void setDraw(String lang) throws LocaleKeyNotFound {
		setTitle(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_DRAW));
		setColor(DEFAULT_EMBED_COLOR);
	}
	protected void setLose(String lang) throws LocaleKeyNotFound {
		setTitle(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_LOSE));
		setColor(LOSE_COLOR);
	}
	protected void setMathResult(Game game, double deposit, String lang) throws LocaleKeyNotFound, GameNotFinished {
		addField(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_DEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
		if (game.isWin()) {
			setWin(lang);
			addField(getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_PRIZE), String.format(DEPOSIT_SHOW_PATTERN, game.getPrize(deposit)), true);
		} else {
			setLose(lang);
		}
	}
	protected void setCustomFooter(UUID uuid, String lang) throws LocaleKeyNotFound {
		try {
			setFooter(String.format(getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_ID), uuid));
		} catch (MissingFormatArgumentException e) {
			getLogger().error(String.format("In language %s error. Message: %s", lang, e.getMessage()));
			setFooter(uuid.toString());
			getLogger().info("Use UUID without notification");
		}
	}
	/**
	 * To shorten <b>MainLocale.getResource</b>
	 * @author Kredwi
	 * @param lang country key (example en_US)
	 * @param key of message in config
	 * @return Localization text from locatiozation file
	 * @throws LocaleKeyNotFound if Localization Key is not Found execute this throw
	 * */
	public final String getLocalText(String lang, LocaleMessagesKeys key) throws LocaleKeyNotFound {
		return getResource(lang, key);
	}
	public final String formatToBold(Object o) {
		return MarkdownUtil.bold(String.valueOf(o));
	}
	
}