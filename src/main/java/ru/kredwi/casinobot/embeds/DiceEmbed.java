package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class DiceEmbed extends EmbedBuilder implements IGameEmbed {
	public DiceEmbed(IGames game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
		String lang = MainLocale.getUserLangFromDB(cmd.getUser(), cmd.getGuild().getLocale().toString());
		
		addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_DEPOSIT), String.valueOf(deposit), true);
		setDescription(String.format(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_DICE_DROPPED), game.getIcon(((int[])game.getGameResult())[1])));
		setCustomFooter(this, game.getGameUUID(), lang);
		
		if (game.isWin()) {
			setTitle(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_WIN));
			setColor(DEFAULT_WIN_COLOR);
			addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_PRIZE), String.format(DEPOSIT_SHOW_PATTERN, game.getPrize(deposit)), true);
		} else {
			setTitle(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_LOSE));
			setColor(DEFAULT_LOSE_COLOR);
		}
	}
}