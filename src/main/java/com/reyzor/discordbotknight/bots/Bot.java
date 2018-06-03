package com.reyzor.discordbotknight.bots;

import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.configuration.BotConfig;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.JDABuilder;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public interface Bot {
    JDABuilder getJDABot();
    AudioPlayerManager getAudioManager();
    ScheduledExecutorService getSchedule();
    void loadSettings() throws IOException, JSONException;
    void writeSettings();
    BotConfig getBotConfig();
    Map<String, ChatCommandIF> addCommand(String command, ChatCommandIF executor);
    Map<String, ChatCommandIF> getCommand();
}
