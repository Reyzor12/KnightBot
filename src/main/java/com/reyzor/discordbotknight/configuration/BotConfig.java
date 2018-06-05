package com.reyzor.discordbotknight.configuration;

import net.dv8tion.jda.core.OnlineStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    @Value("token")
    private String token;
    @Value("audio_enable")
    private Boolean audioEnable;
    @Value("game")
    private String game;
    @Value("status")
    private String status;

    public OnlineStatus getStatus() {
        switch(status)
        {
            case "online" : return OnlineStatus.ONLINE;
            case "idle" : return OnlineStatus.IDLE;
            case "dnd" : return OnlineStatus.DO_NOT_DISTURB;
            case "invisible" : return OnlineStatus.INVISIBLE;
            case "offline"
        }
    }

    public String getGame() { return game; };

    public Boolean getAudioEnable() { return audioEnable; }

    public String getToken() { return token; }

    public String getPrefix()
    {
        return prefix;
    }
}
