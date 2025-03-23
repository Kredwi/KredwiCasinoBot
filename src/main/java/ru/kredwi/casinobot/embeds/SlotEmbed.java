package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.CasinoBot;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.IGames;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class SlotEmbed extends EmbedBuilder implements IGameEmbed {
	
	private final int[][] toIntArray(Object o) {
		if (o instanceof int[][]) {
			return (int[][]) o;
		} else {
			CasinoBot.getLogger().warn("GAMERESULT IS NOT int[][] IN SlotEmbed.java");
			return new int[0][0];
		}
	}
	
	public SlotEmbed(IGames game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
		StringBuilder builder = new StringBuilder();
		String lang = MainLocale.getUserLangFromDB(cmd.getUser(), cmd.getGuild().getLocale().toString());
		int[][] gameResult = toIntArray(game.getGameResult());
		
		for (int[] i : gameResult) {
			for (int j : i) {
				builder.append(game.getIcon(j)).append(" ");
			}
			builder.append("\n");
		}
		
		setDescription(builder.toString());
		addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_DEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
		
		if (game.isWin()) {
			addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_PRIZE), String.format(DEPOSIT_SHOW_PATTERN, game.getPrize(deposit)), true);
			addField(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_STREAK), String.valueOf(game.getWinCount()), true);
			getExampleWin(this, lang);
		} else {
			getExampleLose(this, lang);
		}
		setCustomFooter(this, game.getGameUUID(), lang);
	}
}