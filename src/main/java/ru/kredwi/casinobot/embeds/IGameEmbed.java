package ru.kredwi.casinobot.embeds;

import java.awt.Color;
import java.util.MissingFormatArgumentException;
import java.util.UUID;

import net.dv8tion.jda.api.EmbedBuilder;
import ru.kredwi.casinobot.CasinoBot;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;

public interface IGameEmbed {
	Color DEFAULT_EMBED_COLOR = Color.CYAN;
	Color DEFAULT_WIN_COLOR = Color.YELLOW;
	Color DEFAULT_LOSE_COLOR = Color.RED;
	String DEPOSIT_SHOW_PATTERN = "%.3f";
	
	default EmbedBuilder getExampleWin(EmbedBuilder embed, String lang) throws LocaleKeyNotFound {
		embed.setTitle(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_WIN));
		embed.setColor(DEFAULT_WIN_COLOR);
		return embed;
	}				

	default EmbedBuilder getExampleDraw(EmbedBuilder embed, String lang) throws LocaleKeyNotFound {
		embed.setTitle(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_DRAW));
		embed.setColor(DEFAULT_EMBED_COLOR);
		return embed;
	}
	default EmbedBuilder getExampleLose(EmbedBuilder embed, String lang) throws LocaleKeyNotFound {
		embed.setTitle(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_LOSE));
		embed.setColor(DEFAULT_LOSE_COLOR);
		return embed;
	}
	default EmbedBuilder setCustomFooter(EmbedBuilder embed, UUID uuid, String lang) throws LocaleKeyNotFound {
		try {
			embed.setFooter(String.format(MainLocale.getResource(lang, LocaleMessagesKeys.GAMES_GENERAL_ID), uuid));
		} catch (MissingFormatArgumentException e) {
			CasinoBot.getLogger().error(String.format("In language %s error. Message: %s", lang, e.getMessage()));
			embed.setFooter(uuid.toString());
			CasinoBot.getLogger().info("Use UUID without notification");
		}
		return embed;
	}
}