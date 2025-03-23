package ru.kredwi.casinobot.embeds.alerts;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;

public class AlertEmbed extends EmbedBuilder {

	public AlertEmbed(String description) { this("Alert", description); }
	
	public AlertEmbed(String title, String description) {
		setTitle(title);
		setColor(Color.GREEN);
		setDescription(description);
	}
	
}