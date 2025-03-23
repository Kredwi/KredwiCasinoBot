package ru.kredwi.casinobot.locale;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Function;

import net.dv8tion.jda.api.entities.User;
import ru.kredwi.casinobot.CasinoBot;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.sql.JDBCActions;

public class MainLocale {
	
	private static final String MESSAGES_PATH = "messages/messages";
	
	public static final String getResource(String lang, LocaleMessagesKeys key) throws LocaleKeyNotFound {
		return getResource(lang, key, Function.identity());
	}
	
	public static final <T>T getResource(String lang, LocaleMessagesKeys key, Function<String, T> parser) throws LocaleKeyNotFound {
		Locale locale = Locale.forLanguageTag(lang);
		ResourceBundle resource = ResourceBundle.getBundle(MESSAGES_PATH, locale);
		
		try {
			return parser.apply(resource.getString(key.getPath()));
		} catch (MissingResourceException e) {
			String message = String.format("Key: %s is not found in %s locale", key.getPath(), locale);
			CasinoBot.getLogger().warn(message);
			throw new LocaleKeyNotFound(message);
		}
	}
	public static final String getUserLangFromDB(User user, String defaultLang) {
		String lang = JDBCActions.getUserLanguage(user);
		if (lang == null) {
			return defaultLang;
		} else {
			return lang;
		}
	}
}