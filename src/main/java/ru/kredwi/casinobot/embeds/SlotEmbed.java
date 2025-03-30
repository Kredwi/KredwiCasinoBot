package ru.kredwi.casinobot.embeds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.kredwi.casinobot.CasinoBot;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public class SlotEmbed extends GameEmbedBuilder {
	
	/**
	 * Dad, can you please refactor this?
	 * */
	private final int[][] toIntArray(Object o) {
		if (o instanceof int[][]) {
			return (int[][]) o;
		} else {
			CasinoBot.getLogger().warn("GAMERESULT IS NOT int[][] IN SlotEmbed.java");
			return new int[0][0];
		}
	}
	
	public SlotEmbed(Game game, double deposit, SlashCommandInteractionEvent cmd) throws GameNotFinished, LocaleKeyNotFound {
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
		
		setMathResult(game, deposit, lang);
		
		if (game.isWin()) {
			addField(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_STREAK), String.valueOf(game.getWinCount()), true);
		}
		
		setCustomFooter(game.getGameUUID(), lang);
	}
}