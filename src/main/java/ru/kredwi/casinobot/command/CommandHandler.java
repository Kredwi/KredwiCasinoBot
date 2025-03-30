package ru.kredwi.casinobot.command;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import ru.kredwi.casinobot.CasinoBot;
import ru.kredwi.casinobot.command.slash.BalanceCMD;
import ru.kredwi.casinobot.command.slash.CoinCMD;
import ru.kredwi.casinobot.command.slash.DiceCMD;
import ru.kredwi.casinobot.command.slash.DuelCMD;
import ru.kredwi.casinobot.command.slash.LanguageCMD;
import ru.kredwi.casinobot.command.slash.RPSCMD;
import ru.kredwi.casinobot.command.slash.SalaryCMD;
import ru.kredwi.casinobot.command.slash.SlotCMD;
import ru.kredwi.casinobot.command.slash.TopCMD;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.game.data.RPSData;
import ru.kredwi.casinobot.locale.LocaleMessagesKeys;
import ru.kredwi.casinobot.locale.MainLocale;
import ru.kredwi.casinobot.sql.JDBCActions;

public class CommandHandler {
	
	private static CommandHandler instance;
	
	private Map<String, RPSData> GAMES = new HashMap<>();
	
	private final Map<String, ISlashCommand> commands = new HashMap<String, ISlashCommand>();
	
	private CommandHandler() {
		ISlashCommand[] commands = new ISlashCommand[] {
				new SlotCMD(), new CoinCMD(), new DiceCMD(),
				new RPSCMD(), new BalanceCMD(), new SalaryCMD(),
				new DuelCMD(), new TopCMD(), new LanguageCMD()
		};
		
		for (ISlashCommand command : commands) {
			this.commands.put(command.getData().getName(), command);
		}
	}
	
	public static CommandHandler getInstance() {
		if (instance == null) {
			instance = new CommandHandler();
		}
		return instance;
	}
	
	public final void run() {
		CommandListUpdateAction commands = CasinoBot.getBot().updateCommands();
		
		for (Map.Entry<String, ISlashCommand> entry : this.commands.entrySet()) {
			commands.addCommands(entry.getValue().getData());
		}
		
		commands.queue();
	}
	
	public final void getAndExecuteCommand(@NotNull SlashCommandInteractionEvent commandEvent) {
		try {
			ISlashCommand command = commands.get(commandEvent.getName().toLowerCase());
			
			User user = commandEvent.getUser();
			
			String guildLang = commandEvent.getGuild().getLocale().getLocale();
			
			JDBCActions.addIfNotExistsUser(guildLang, user.getName(), user.getIdLong());
			
			String lang = MainLocale.getUserLangFromDB(user, guildLang);
			
			commandEvent.reply(MainLocale.getResource(lang, LocaleMessagesKeys.BOT_COMMANDS_GENERAL_WAITING)).queue();
			
			if (command != null) {
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						command.execute(commandEvent, lang);
					}
				});
				thread.setName("CommandExecute");
				thread.start();
			}
		} catch (LocaleKeyNotFound e) {
			e.printStackTrace();
			commandEvent.getHook().editOriginal("Oh... locale... no..").queue();;
		}
	}

	public RPSData getGames(String gameID) {
		return GAMES.get(gameID);
	}
	public void setGames(String gameID, RPSData data) {
		this.GAMES.put(gameID, data);
	}
}