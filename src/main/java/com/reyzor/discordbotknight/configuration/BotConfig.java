package com.reyzor.discordbotknight.configuration;

import net.dv8tion.jda.core.OnlineStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.DefaultPropertiesPersister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Configuration for class, which realize interface {@link com.reyzor.discordbotknight.bots.Bot}
 * example {@link com.reyzor.discordbotknight.bots.BaseBot}
 * @author Reyzor
 * @version 1.0
 * @since 27.05.2018
 */

@Configuration
@PropertySource("classpath:config.properties")
public class BotConfig
{
    @Value("${prefix}")
    private String prefix;
    @Value("${token}")
    private String token;
    @Value("${audio_enable}")
    private String audioEnable;
    @Value("${game}")
    private String game;
    @Value("${status}")
    private String status;

    private final static String BOT_CONFIG = "config.properties";

    public OnlineStatus getStatus() {
        return getStatus(status);
    }

    public OnlineStatus getStatus(String st) {
        switch(st)
        {
            case "online" : return OnlineStatus.ONLINE;
            case "idle" : return OnlineStatus.IDLE;
            case "dnd" : return OnlineStatus.DO_NOT_DISTURB;
            case "invisible" : return OnlineStatus.INVISIBLE;
            case "offline" : return OnlineStatus.OFFLINE;
            default : return OnlineStatus.UNKNOWN;
        }
    }

    public String getGame() { return game; }

    public Boolean getAudioEnable() { return audioEnable.equals("true") ? true : false; }

    public String getToken() { return token; }

    public String getPrefix()
    {
        return prefix;
    }

    public void setProperty(String property, String value)
    {
        Properties properties = new Properties();
        properties.setProperty("prefix", prefix);
        properties.setProperty("token", token);
        properties.setProperty("audio_enable", audioEnable);
        properties.setProperty("game", game);
        properties.setProperty("status",status);
        properties.setProperty(property, value);
        try {
            final DefaultPropertiesPersister persister = new DefaultPropertiesPersister();
            File file = new File(getClass().getClassLoader().getResource(BOT_CONFIG).getPath());
            OutputStream out = new FileOutputStream(file);
            persister
                    .store(
                            properties,
                            out,
                            null
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
