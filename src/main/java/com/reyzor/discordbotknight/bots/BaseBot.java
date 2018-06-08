package com.reyzor.discordbotknight.bots;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.configuration.BotConfig;
import com.reyzor.discordbotknight.configuration.BotSettings;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Define simplest implementation of interface {@link Bot} for use in Discord
 * @author Reyzor
 * @version 1.0
 * @since 27.05.2018
 */
@Component
public class BaseBot extends ListenerAdapter implements Bot {

    @Autowired
    private BotConfig config;

    private final static String SERVER_SETTINGS = "serversettings.json";

    private final AudioPlayerManager audioManager;
    private final HashMap<String, BotSettings> botSettings;
    private final ScheduledExecutorService schedule;
    private final Map<String, ChatCommandIF> commands;

    private JDABuilder bot;
    private JDA jda;

    public BaseBot()
    {
        botSettings  = new HashMap<>();
        commands     = new HashMap<>();
        audioManager = new DefaultAudioPlayerManager();
        schedule     = Executors.newSingleThreadScheduledExecutor();

        setupAudioManager();

        try {
            writeSettings();
            loadSettings();
        } catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public JDABuilder getJDABot()
    {
        return bot;
    }
    @Override
    public AudioPlayerManager getAudioManager() {return audioManager;}
    @Override
    public ScheduledExecutorService getSchedule() {return schedule;}
    @Override
    public void loadSettings() throws IOException, JSONException
    {
        JSONObject loadedSettings = new JSONObject(new String(Files.readAllBytes(Paths.get(SERVER_SETTINGS))));
        loadedSettings.keySet().forEach(id ->
        {
            JSONObject setting = loadedSettings.getJSONObject(id);
            botSettings.put(id, new BotSettings(
               setting.has("text_channel_id")  ? setting.getString("text_channel_id")  : null,
               setting.has("voice_channel_id") ? setting.getString("voice_channel_id") : null,
               setting.has("dj_role_id")       ? setting.getString("dj_role_id")       : null,
               setting.has("volume")           ? setting.getInt("volume")              : null,
               setting.has("default_playlist") ? setting.getString("default_playlist") : null,
               setting.has("repeat")           ? setting.getBoolean("repeat")          : false
            ));
        });
    }
    @Override
    public void writeSettings()
    {
        JSONObject newSettings = new JSONObject();
        botSettings.keySet().stream().forEach(key ->
        {
            JSONObject saveSettings = new JSONObject();
            BotSettings settings = botSettings.get(key);
            if (settings.getTextId() != 0) saveSettings.put("text_channel_id", Long.toString(settings.getTextId()));
            if (settings.getVoiceId() != 0) saveSettings.put("voice_channel_id", Long.toString(settings.getVoiceId()));
            if (settings.getRoleId() != 0) saveSettings.put("dj_role_id", Long.toString(settings.getRoleId()));
            if (settings.getVolume() != 50) saveSettings.put("volume", settings.getVolume());
            if (settings.getDefaultPlaylist() != null) saveSettings.put("default_playlist", settings.getDefaultPlaylist());
            if (settings.getRepeatMode()) saveSettings.put("repeat", true);
            newSettings.put(key, saveSettings);
        });
        try
        {
            Files.write(Paths.get("serversettings.json"), newSettings.toString(4).getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onReady(ReadyEvent event)
    {
        this.jda = event.getJDA();
        checkGuild(this.jda);
    }

    private void checkGuild(JDA jda)
    {
        if (jda.getGuilds().isEmpty())
        {
            Logger log = LoggerFactory.getLogger("Knight Bot");
            log.warn("This bot is not member of any guilds!");
        }
    }
   /* private void checkForBotInThisGuild(JDA jda)
    {
        Guild otherBots = jda.getGuildById(110373943822540800L);
        if (otherBots == null) return;
        //if ()
    }*/
    @Override
    public BotConfig getBotConfig()
    {
        return this.config;
    }

    @Override
    public BotSettings getBotSettings(Guild guild)
    {
        return botSettings.getOrDefault(guild.getId(), BotSettings.DEFAULT_SETTINGS);
    }

    @Override
    public int queueTrack(ChatCommandIF command, AudioTrack track) {
        return setUpHandler(command).addTrack(track, command.getAuthor());
    }

    @Override
    public Map<String, ChatCommandIF> addCommand(String command, ChatCommandIF executor) {
        commands.putIfAbsent(command,executor);
        return commands;
    }

    @Override
    public Map<String, ChatCommandIF> getCommand() {
        return commands;
    }

    /**
     * Define and setting default audio manager
     * For base realization use {@link DefaultAudioPlayerManager}
     * @see AudioPlayerManager
     * */

    private void setupAudioManager()
    {
        AudioSourceManagers.registerRemoteSources(audioManager);
        AudioSourceManagers.registerLocalSource(audioManager);
        audioManager.source(YoutubeAudioSourceManager.class).setPlaylistPageCount(10);
    }

    public AudioHandler setUpHandler(ChatCommandIF command)
    {
        return setUpHandler(command.getGuild());
    }

    public AudioHandler setUpHandler(Guild guild)
    {
        AudioHandler handler;
        if (guild.getAudioManager().getSendingHandler() == null)
        {
            AudioPlayer player = audioManager.createPlayer();
            if (botSettings.containsKey(guild.getId())) player.setVolume(botSettings.get(guild.getId()).getVolume());
            handler = new AudioHandler(player, this, guild);
            player.addListener(handler);
            guild.getAudioManager().setSendingHandler(handler);
        }
        else handler = (AudioHandler) guild.getAudioManager().getSendingHandler();
        return handler;
    }
}
