package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.enums.RPSEnum;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class RPSEmbed extends EmbedBuilder implements IGameEmbed {

	public RPSEmbed(IGames game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
		RPSEnum[] gameResult = (RPSEnum[]) game.getGameResult();
		String lang = MainLocale.getUserLangFromDB(cmd.getUser(), cmd.getGuild().getLocale().toString());
		
		StringBuilder descriptionBuilder = new StringBuilder();
		descriptionBuilder.append(String.format(
				MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_RSP_CHOICE),
				game.getIcon(gameResult[0].ordinal())));
		descriptionBuilder.append("\n");
		descriptionBuilder.append(String.format(
				MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_RSP_OPCHOICE),
				game.getIcon(gameResult[1].ordinal())));
		
		setCustomFooter(this, game.getGameUUID(), lang);
		setDescription(descriptionBuilder.toString());
		addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_DEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
		
		if (game.isWin()) {
			getExampleWin(this, lang);
			addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_WIN), String.format(DEPOSIT_SHOW_PATTERN, game.getPrize(deposit)), true);
		} else {
			if (gameResult[0].equals(gameResult[1])) {
				getExampleDraw(this, lang);
				addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_RETURNDEPOSIT), String.format(DEPOSIT_SHOW_PATTERN, deposit), true);
			} else {
				getExampleLose(this, lang);
			}
		}
	}
	
}