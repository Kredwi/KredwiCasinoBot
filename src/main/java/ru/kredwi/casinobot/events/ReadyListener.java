package ru.kredwi.casinobot.events;

import java.util.Scanner;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.kredwi.casinobot.CasinoBot;

public class ReadyListener extends ListenerAdapter {
	@Override
	public void onReady(ReadyEvent event) {
		User bot = event.getJDA().getSelfUser();
		long startedTime = getUpTime();
		CasinoBot.getLogger().info(String.format("%s started. Starting in %sms", bot.getName(), startedTime));
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				while (true) {
					String command = scan.nextLine();
					switch (command.toLowerCase()) {
					case "uptime": {
						CasinoBot.getLogger().info("Uptime: " + String.valueOf(getUpTime()) + "ms");
						break;
					}
					case "threadcount": {
						CasinoBot.getLogger().info("Active Thread: " + String.valueOf(Thread.activeCount()));
						break;
					}
					case "stop": {
						CasinoBot.getLogger().warn(bot.getName() + " Stopping...");
						System.exit(1);
						break;
					}
					default: CasinoBot.getLogger().error("Command " + command + " not found");
					}
				}
			}
		});
		thread.setName("BotCommandListener");
		thread.start();
	}
	private long getUpTime() {
		return System.currentTimeMillis() - CasinoBot.BOT_STATRED_TIME;
	}
}