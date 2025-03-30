package ru.kredwi.casinobot.command.slash;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import ru.kredwi.casinobot.command.IErrorCommand;
import ru.kredwi.casinobot.command.ISlashCommand;
import ru.kredwi.casinobot.embeds.SlotEmbed;
import ru.kredwi.casinobot.exception.GameNotFinished;
import ru.kredwi.casinobot.exception.LocaleKeyNotFound;
import ru.kredwi.casinobot.games.Game;
import ru.kredwi.casinobot.games.Slot;
import ru.kredwi.casinobot.sql.JDBCActions;

public class SlotCMD implements ISlashCommand, IErrorCommand {

	@Override
	public SlashCommandData getData() {
		return Commands.slash(SLOT_GAME_COMMAND, "Spin it's slots! is free xD")
				.addOption(OptionType.NUMBER, "deposit", "Write your deposit!", true);
	}

	@Override
	public void execute(@NotNull SlashCommandInteractionEvent commandEvent, String lang) {
		
		final User user = commandEvent.getUser();
		double deposit = commandEvent.getOption("deposit").getAsDouble();
		
		if (JDBCActions.getUserBalance(user) < deposit) {
			commandEvent.getHook().editOriginal("").setEmbeds(noMoneyGetEmbed(deposit, lang)).queue();
			return;
		} else JDBCActions.deleteUserBalance(user.getIdLong(), deposit);
		
		Game slot = new Slot();
		
		try {
			if (slot.isWin()) {
				JDBCActions.addUserWin(user.getIdLong(), 1);
				JDBCActions.addUserBalance(user.getIdLong(), slot.getPrize(deposit) + deposit);
			} else {
				JDBCActions.addUserLose(user.getIdLong(), 1);
			}
			MessageEmbed embed = new SlotEmbed(slot, deposit, commandEvent).build();
			
			commandEvent.getHook().editOriginal("").setEmbeds(embed).queue();
		} catch (GameNotFinished | LocaleKeyNotFound e) {
			exception(commandEvent, e.getMessage(), lang);
		}
	}

}