package ru.kredwi.casinobot.embeds.rps;

import java.awt.Color;
import java.time.Instant;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import ru.kredwi.casinobot.embeds.GameEmbedBuilder;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;

public class RPSSelectEmbed extends GameEmbedBuilder {
	
	public RPSSelectEmbed(Pair<User, User> players, double deposit, String lang) throws GameNotFinished, LocaleKeyNotFound {
	
		setTitle(String.format(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_VS), players.getLeft().getName(), players.getRight().getName()));
		setDescription(String.format(getLocalText(lang, LocaleMessagesKeys.GAMES_RSP_SELECT_CHOICE), players.getLeft().getName()));
		setColor(Color.LIGHT_GRAY);
		setTimestamp(Instant.now());
		addField(getLocalText(lang, LocaleMessagesKeys.GAMES_GENERAL_DEPOSIT), String.format(DEPOSIT_SHOW_PATTERN,deposit), true);
	}
	
}
