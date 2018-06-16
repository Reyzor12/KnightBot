package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Check {@link com.reyzor.discordbotknight.bots.Bot} can use
 * {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayer}
 * @author Reyzor
 * @version 1.0
 * @since 15.06.2018
 */

public class BotAudioChecker extends Checker
{
    private Bot bot;

    public BotAudioChecker(Bot bot)
    {
        super();
        this.bot = bot;
    }
    public BotAudioChecker(Bot bot, Checker checker)
    {
        super(checker);
        this.bot = bot;
    }

    @Override
    public boolean check(MessageReceivedEvent event) {
        if (!bot.getBotConfig().getAudioEnable())
        {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_CANT_USE_AUDIO.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
