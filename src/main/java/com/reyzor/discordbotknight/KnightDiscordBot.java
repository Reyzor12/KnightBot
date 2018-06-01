package com.reyzor.discordbotknight;

import com.reyzor.discordbotknight.bots.BaseBot;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.BaseCommand;
import com.reyzor.discordbotknight.configuration.Context;
import com.reyzor.discordbotknight.configuration.ContextConfiguration;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

/**
 * @author Reyzor
 * @version 1.0
 * @since 26.05.2018
 */

public class KnightDiscordBot
{
    private static final Class CONFIGURATION_CLASS = ContextConfiguration.class;
    //private Bot bot;
    private static Context context;

    public static void main(String[] args) {
        if (!initContext(CONFIGURATION_CLASS)) return;
        Bot bot = (new BaseBot()).createBot();
        try
        {
            new JDABuilder(AccountType.BOT)
                    .setToken("NDQxMTk3NDEzMDA1NTI0OTk0.Dcs4ow.hbSSeX8kAJr4b2yI4LbgdG-5pXA")
                    .setAudioEnabled(true)
                    .setGame(Game.playing("Minecraft"))
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .addEventListener(bot)
                    //.addEventListener(new BaseCommand())
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
