package ru.kredwi.casinobot.embeds.accept;

import java.awt.Color;
import java.time.Instant;

public class RPSGAEmbed extends GameAcceptBuilder {

	public RPSGAEmbed() {
		setTitle("Подтвердите игру!");
		setColor(Color.GRAY);
		setTimestamp(Instant.now());
	}
	
}
