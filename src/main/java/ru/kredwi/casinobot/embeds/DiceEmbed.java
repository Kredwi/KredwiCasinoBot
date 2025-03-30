package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class DiceEmbed extends GameEmbedBuilder {
	public DiceEmbed(Game game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
		String lang = MainLocale.getUserLangFromDB(cmd.getUser(), cmd.getGuild().getLocale().toString());
		
		setDescription(String.format(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_DICE_DROPPED), game.getIcon(((int[])game.getGameResult())[1])));
		setCustomFooter(game.getGameUUID(), lang);
		
		setMathResult(game, deposit, lang);
	}
}