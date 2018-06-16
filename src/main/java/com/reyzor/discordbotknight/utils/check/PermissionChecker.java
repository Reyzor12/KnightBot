package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Class for checker permission of {@link net.dv8tion.jda.core.entities.User} in
 * {@link net.dv8tion.jda.core.entities.Guild}
 * @author Reyzor
 * @version 1.0
 * @since 15.06.2018
 */

public class PermissionChecker extends Checker
{
    @Override
    public boolean check(MessageReceivedEvent event)
    {
        if (!MessageUtil.checkPermission(event))
        {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
