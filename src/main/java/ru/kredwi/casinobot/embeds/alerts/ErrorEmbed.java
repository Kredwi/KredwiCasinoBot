package ru.kredwi.casinobot.embeds.alerts;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;

public class ErrorEmbed extends EmbedBuilder {
	
	public ErrorEmbed(String description) { this("So bad", description); }
	
	public ErrorEmbed(String title, String description) {
		setTitle(title);
		setColor(Color.RED);
		setDescription(description);
	}
}