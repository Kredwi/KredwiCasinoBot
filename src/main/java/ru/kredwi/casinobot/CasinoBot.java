package ru.kredwi.casinobot;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;

import org.slf4j.Logger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;
import ru.kredwi.casinobot.button.ButtonHandler;
import ru.kredwi.casinobot.command.CommandHandler;
import ru.kredwi.casinobot.listener.ButtonListener;
import ru.kredwi.casinobot.listener.ReadyListener;
import ru.kredwi.casinobot.listener.SlashCommandListener;
import ru.kredwi.casinobot.sql.DBInitilization;

/**
 * @author Kredwi
 * Hello world!
 */
public class CasinoBot {
  
  public static final long BOT_STATRED_TIME = System.currentTimeMillis();
  
  private static Logger LOGGER = JDALogger.getLog(CasinoBot.class);
  
  public static Properties config = new Properties();
  
  private static JDA jda;
  
  private DBInitilization dBInit;
  
  private CommandHandler commandHandler;
  
  private ButtonHandler buttonHandler;
  
  public CasinoBot(DBInitilization dBInit, CommandHandler commandHandler, ButtonHandler buttonHandler) {
    this.dBInit = dBInit;
    this.commandHandler = commandHandler;
    this.buttonHandler = buttonHandler;
  }
  
  public static void main( String[] args ) {
    
    try (InputStream is = CasinoBot.class.getResourceAsStream("/config.properties")) {
      config.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    DBInitilization database = new DBInitilization();
    CommandHandler commandHandler = CommandHandler.getInstance();
    ButtonHandler buttonHandler = ButtonHandler.getInstance();
    
    CasinoBot casinoBot = new CasinoBot(database, commandHandler, buttonHandler);
    
    casinoBot.start();
    }
    
  private void start() {
    try {
      jda = JDABuilder.createLight(config.getProperty("token"), getGatewayIntent())
//          .addEventListeners(new MessageReceivedListener())
          .addEventListeners(new SlashCommandListener())
          .addEventListeners(new ButtonListener())
          .addEventListeners(new ReadyListener())
          .setRequestTimeoutRetry(true)
          .build()
          .awaitReady();
      
      dBInit.init();
      LOGGER.info("Database is ready");
      commandHandler.run();
      LOGGER.info("Commands is ready");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
    private final EnumSet<GatewayIntent> getGatewayIntent() {
      return EnumSet.of(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES);
    }
    
    public static JDA getBot() {
      return jda;
    }
    
    public static final Logger getLogger() {
      return LOGGER;
    }
}