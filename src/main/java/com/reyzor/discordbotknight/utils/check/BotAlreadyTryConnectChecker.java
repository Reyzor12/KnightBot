package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/** Check if {@link com.reyzor.discordbotknight.bots.Bot} is trying connect to
 * {@link net.dv8tion.jda.core.entities.Guild} audio channel
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public class BotAlreadyTryConnectChecker extends Checker
{
    @Override
    public boolean check(MessageReceivedEvent event)
    {
        if (event.getGuild().getAudioManager().isAttemptingToConnect())
        {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_ALREADY_TRY_JOIN_CHANNEL.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
