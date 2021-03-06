package com.reyzor.discordbotknight.bots;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.configuration.BotConfig;
import com.reyzor.discordbotknight.configuration.BotSettings;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Common interface for all instantiate of all bots
 * methods setup base functionality of bot and help get meta information
 *
 * @author reyzor
 * @since 04.06.2018
 * @version 1.0
 * */

public interface Bot {

    /**
     * method for loading settings from json file
     * */
    void loadSettings() throws IOException, JSONException;
    /**
     * method to write settings to json file,
     * the parameter which have to write is defined by realization of {@link Bot} interface;
     * */
    void writeSettings();

    /**
     * Add command to chat
     * @param command - name of command for execute{@link ChatCommandIF#execute(MessageReceivedEvent, String)}
     * @param executor - object implements interface {@link ChatCommandIF} to execute command
     * @return map, where key - string command, value - object {@link ChatCommandIF}
     * {@link ChatCommandIF} выполнябщийе команды
     * */
    Map<String, ChatCommandIF> addCommand(String command, ChatCommandIF executor);
    /**
     * @return map of pairs key - command, value - object {@link ChatCommandIF}
     * */
    Map<String, ChatCommandIF> getCommand();


    JDABuilder getJDABot();

    AudioPlayerManager getAudioManager();

    ScheduledExecutorService getSchedule();

    /**
     * @return object - configuration for implementation of bot interface
     * @see BotConfig
     * */

    BotConfig getBotConfig();

    BotSettings getBotSettings(Guild guild);

    /** Command for define position track {@link AudioTrack} at track list
     * @return track position at track list
     * */

    int queueTrack(ChatCommandIF command, AudioTrack track);

    /**
     * Setup for guild {@link Guild} audio {@link AudioHandler}
     * return audio handler for guild
     * */

    AudioHandler setUpHandler(ChatCommandIF command);
    AudioHandler setUpHandler(Guild guild);

    /**
     * Set volume for {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayer} at
     * {@link AudioHandler} for bot {@link Bot} at guild {@link Guild}
     * */

    void setVolume(Guild guild, Integer volume);
}
