package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.enums.RPSEnum;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class RPSEmbed extends GameEmbedBuilder {

	public RPSEmbed(IGames game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
		RPSEnum[] gameResult = (RPSEnum[]) game.getGameResult();
		String lang = MainLocale.getUserLangFromDB(cmd.getUser(), cmd.getGuild().getLocale().toString());
		
		StringBuilder descriptionBuilder = new StringBuilder();
		descriptionBuilder.append(String.format(
				getLocalText(lang, LocaleMessagesKeys.GAMES_RSP_CHOICE),
				game.getIcon(gameResult[0].ordinal())));
		descriptionBuilder.append("\n");
		descriptionBuilder.append(String.format(
				getLocalText(lang, LocaleMessagesKeys.GAMES_RSP_OPCHOICE),
				game.getIcon(gameResult[1].ordinal())));
		
		setCustomFooter(game.getGameUUID(), lang);
		setDescription(descriptionBuilder.toString());
		if (gameResult[0].equals(gameResult[1])) {
			setDraw(lang);
			addField(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_RETURNDEPOSIT), String.format(DEPOSIT_SHOW_PATTERN, deposit), true);
			return;
		}
		
		setMathResult(game, deposit, lang);
	}
	
}