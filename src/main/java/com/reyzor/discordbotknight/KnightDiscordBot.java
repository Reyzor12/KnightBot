package com.reyzor.discordbotknight;

import com.reyzor.discordbotknight.bots.BaseBot;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.configuration.Context;
import com.reyzor.discordbotknight.configuration.ContextConfiguration;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

/**
 * Main class - start app
 * In main spring context is checked and after that initialize
 * when run bot
 * @author Reyzor
 * @version 1.0
 * @since 26.05.2018
 */

public class KnightDiscordBot
{
    private static final Class  CONFIGURATION_CLASS = ContextConfiguration.class;
    private static final String COMMAND_BEAN        = "clientCommand";
    private static final String BASE_BOT = "baseBot";
    private static Bot bot;
    private static Context context;

    public static void main(String[] args) {
        if (!initContext(CONFIGURATION_CLASS)) return;
        bot = (BaseBot) context.getApplicationContext().getBean(BASE_BOT);
        try
        {
            new JDABuilder(AccountType.BOT)
                    .setToken(bot.getBotConfig().getToken())
                    .setAudioEnabled(true)
                    .setGame(Game.playing(bot.getBotConfig().getGame()))
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .addEventListener(bot)
                    .addEventListener(context.getApplicationContext().getBean(COMMAND_BEAN) )
                    .buildAsync();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private static boolean initContext(Class clazz)
    {
        context = Context.getInstance();
        if (context.initContext(clazz) == null) return false;
        return true;
    }
}
