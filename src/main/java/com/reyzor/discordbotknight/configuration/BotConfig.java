package com.reyzor.discordbotknight.configuration;

import net.dv8tion.jda.core.OnlineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Class is configuration of bot
 * @author Reyzor
 * @version 1.0
 * @since 27.05.2018
 */

@Configuration
@PropertySource("classpath:config.properties")
public class BotConfig
{
    private final static Logger logger = LoggerFactory.getLogger(BotConfig.class);

    @Autowired
    private Environment properties;
    private String configLocation = null;
    @Value("${prefix}")
    private String prefix;
    private OnlineStatus status = OnlineStatus.UNKNOWN;


    public BotConfig()
    {

    }

    public String getPrefix()
    {
        return prefix;
    }
}
