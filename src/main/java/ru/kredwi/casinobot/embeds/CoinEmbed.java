package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.enums.CoinSide;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class CoinEmbed extends GameEmbedBuilder {
	
	public CoinEmbed(IGames game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
		CoinSide[] gameResult = (CoinSide[]) game.getGameResult();
		String lang = MainLocale.getUserLangFromDB(cmd.getUser(), cmd.getGuild().getLocale().toString());
		
		setDescription(String.format(getLocalText(
				lang, LocaleMessagesKeys.GAMES_COIN_RESULT
				), formatToBold(gameResult[0].toString()),  formatToBold(gameResult[1].toString())));
		
		setMathResult(game, deposit, lang);
		setCustomFooter(game.getGameUUID(), lang);
	}
}