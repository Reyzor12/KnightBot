package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/** Check if {@link com.reyzor.discordbotknight.bots.Bot} can join to
 * {@link net.dv8tion.jda.core.entities.VoiceChannel}
 * @author Reyzor
 * @version 1.0
 * @since 15.06.2018
 */

public class PermissionToJoinVoiceChannelChecker extends Checker
{
    public PermissionToJoinVoiceChannelChecker() { super(); }
    public PermissionToJoinVoiceChannelChecker(Checker checker) { super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event)
    {
        if (!MessageUtil.hasPermissionToJoinVoiceChannel(event))
        {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_PERMISSION_JOIN_VOICE_CHANNEL.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
