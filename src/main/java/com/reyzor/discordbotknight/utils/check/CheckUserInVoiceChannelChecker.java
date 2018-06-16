package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Check member {@link net.dv8tion.jda.core.entities.Member}
 * in voice channel {@link net.dv8tion.jda.core.entities.VoiceChannel}
 * @author Reyzor
 * @version 1.0
 * @since 15.06.2018
 */

public class CheckUserInVoiceChannelChecker extends Checker
{
    public CheckUserInVoiceChannelChecker() { super(); }
    public CheckUserInVoiceChannelChecker(Checker checker) { super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event)
    {
        if (!MessageUtil.checkMemberVoiceChatConnection(event))
        {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
