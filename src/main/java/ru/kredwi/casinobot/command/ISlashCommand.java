package ru.kredwi.casinobot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface ISlashCommand {
	
	String SALARY_COMMAND = "salary";
	String BALANCE_COMMAND = "balance";
	String LANGUAGE_COMMAND = "language";
	
	String TOP_COMMAND = "top";
	
	String SLOT_GAME_COMMAND = "slot";
	String COIN_GAME_COMMAND = "coin";
	String RPS_GAME_COMMAND = "rps";
	String DICE_GAME_COMMAND = "dice";
	String DUEL_GAME_COMMAND = "duel";
	
	/**
	 * Command Metadates
	 * @author Kredwi
	 * @return Command data
	 * */
	SlashCommandData getData();
	
	/**
	 * Command Interaction Execute
	 * @author Kredwi
	 * @param commandEvent command event data
	 * @param lang picked user language
	 * @return void
	 * */
	void execute(SlashCommandInteractionEvent commandEvent, String lang);
	
	/**
	 * Embed Exception Handler
	 * @author Kredwi
	 * @param commandEvent command event data
	 * @param message messages for write
	 * @return void
	 * */
}
