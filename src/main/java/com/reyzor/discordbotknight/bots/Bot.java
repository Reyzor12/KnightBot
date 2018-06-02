package com.reyzor.discordbotknight.bots;

import com.reyzor.discordbotknight.configuration.BotConfig;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.JDABuilder;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

public interface Bot {
    JDABuilder getJDABot();
    AudioPlayerManager getAudioManager();
    ScheduledExecutorService getSchedule();
    void loadSettings() throws IOException, JSONException;
    void writeSettings();
    BotConfig getBotConfig();
}
