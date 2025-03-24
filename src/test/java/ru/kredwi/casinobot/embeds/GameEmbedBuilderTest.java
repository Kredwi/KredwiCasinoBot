package ru.kredwi.casinobot.embeds;

import java.awt.Color;

import junit.framework.TestCase;
import net.dv8tion.jda.api.EmbedBuilder;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;


/**
 * I didn't just download it for no reason
 * */
public class GameEmbedBuilderTest extends TestCase {

	private static final String LANGUAGE = "en_US";
	private GameEmbedBuilder builder;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		builder = new GameEmbedBuilder();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		builder = null;
	}
	
	public void testGetValue() throws LocaleKeyNotFound {
		String textFromAbstractMethod = builder.getLocalText(LANGUAGE, LocaleMessagesKeys.BOT_COMMANDS_BALANCE);
		String textFromMethod = MainLocale.getResource(LANGUAGE, LocaleMessagesKeys.BOT_COMMANDS_BALANCE);
		assertEquals(textFromAbstractMethod, textFromMethod);
	}
	
	public void testSetWin() throws LocaleKeyNotFound {
		EmbedBuilder embedFromMethod = builder;
		builder.setWin(LANGUAGE);
		
		EmbedBuilder embedBuilder = this.formatEmbedBuilder(GameEmbedBuilder.WIN_COLOR, LocaleMessagesKeys.GAMES_GENERAL_WIN);
		
		assertEquals(embedFromMethod.build(), embedBuilder.build());
	}
	
	public void testSetDraw() throws LocaleKeyNotFound {
		EmbedBuilder embedFromMethod = builder;
		builder.setDraw(LANGUAGE);
		
		EmbedBuilder embedBuilder = this.formatEmbedBuilder(GameEmbedBuilder.DEFAULT_EMBED_COLOR, LocaleMessagesKeys.GAMES_GENERAL_DRAW);
		
		assertEquals(embedFromMethod.build(), embedBuilder.build());
	}
	public void testSetLose() throws LocaleKeyNotFound {
		EmbedBuilder embedFromMethod = builder;
		builder.setLose(LANGUAGE);
		
		EmbedBuilder embedBuilder = this.formatEmbedBuilder(GameEmbedBuilder.LOSE_COLOR, LocaleMessagesKeys.GAMES_GENERAL_LOSE);
		
		assertEquals(embedFromMethod.build(), embedBuilder.build());
	}
	
	private EmbedBuilder formatEmbedBuilder(Color color, LocaleMessagesKeys localeKey) throws LocaleKeyNotFound {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle(MainLocale.getResource(LANGUAGE, localeKey));
		embedBuilder.setColor(color);
		return embedBuilder;
	}
}
