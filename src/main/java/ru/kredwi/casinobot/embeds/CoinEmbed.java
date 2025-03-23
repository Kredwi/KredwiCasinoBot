package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import ru.kredwi.casinobot.enums.CoinSide;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class CoinEmbed extends EmbedBuilder implements IGameEmbed {
	
	public CoinEmbed(IGames game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
		CoinSide[] gameResult = (CoinSide[]) game.getGameResult();
		String lang = MainLocale.getUserLangFromDB(cmd.getUser(), cmd.getGuild().getLocale().toString());
		
		setCustomFooter(this, game.getGameUUID(), lang);
		setDescription(String.format(getValue(
				lang, LocaleMessagesKeys.GAMES_COIN_RESULT
				), MarkdownUtil.bold(gameResult[0].toString()),  MarkdownUtil.bold(gameResult[1].toString())));
		
		addField(getValue(lang, LocaleMessagesKeys.GAMES_GENERAL_DEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
		if (game.isWin()) {
			addField(getValue(lang, LocaleMessagesKeys.GAMES_GENERAL_PRIZE), String.format(DEPOSIT_SHOW_PATTERN, game.getPrize(deposit)), true);
			getExampleWin(this, lang);
		} else {
			getExampleLose(this, lang);
		}
	}
	
	private final String getValue(String lang, LocaleMessagesKeys key) throws LocaleKeyNotFound {
		return MainLocale.getResource(lang, key);
	}
}