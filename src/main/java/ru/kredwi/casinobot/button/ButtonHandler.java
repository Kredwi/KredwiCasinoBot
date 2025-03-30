package ru.kredwi.casinobot.button;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import ru.kredwi.casinobot.button.rps.RPSPaperButton;
import ru.kredwi.casinobot.button.rps.RPSScissorsButton;
import ru.kredwi.casinobot.button.rps.RPSStoneButton;

public class ButtonHandler {
	
	private static ButtonHandler instance;
	
	private final Map<String, IButton> allButton = new HashMap<String, IButton>();
	
	private ButtonHandler() {
		IButton[] buttons = new IButton[] {
				new RPSPaperButton(), new RPSScissorsButton(), new RPSStoneButton() // rps
		};
		
		for (IButton button : buttons) {
			this.allButton.put(button.getData().getId(), button);
		}
	}
	
	public static ButtonHandler getInstance() {
		if (instance == null) {
			instance = new ButtonHandler();
		}
		return instance;
	}
	
	public void executeButton(ButtonInteractionEvent buttonEvent) {
		IButton button = allButton.get(buttonEvent.getButton().getId());
		if (button != null) {
			button.execute(buttonEvent);
		}
	}
	
	public Button getButton(String buttonID) {
		return this.allButton.get(buttonID).getData();
	}
	
}
