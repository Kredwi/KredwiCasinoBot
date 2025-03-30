package ru.kredwi.casinobot;

import java.util.EnumSet;
import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;

public class Bot implements User {

	@Override
	public String getDefaultAvatarId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsMention() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getIdLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bot";
	}

	@Override
	public String getGlobalName() {
		// TODO Auto-generated method stub
		return "CasinoBot";
	}

	@Override
	public String getDiscriminator() {
		// TODO Auto-generated method stub
		return "0";
	}

	@Override
	public String getAvatarId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CacheRestAction<Profile> retrieveProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsTag() {
		// TODO Auto-generated method stub
		return "bot";
	}

	@Override
	public boolean hasPrivateChannel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CacheRestAction<PrivateChannel> openPrivateChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Guild> getMutualGuilds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSystem() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JDA getJDA() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<UserFlag> getFlags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFlagsRaw() {
		// TODO Auto-generated method stub
		return 0;
	}

}
