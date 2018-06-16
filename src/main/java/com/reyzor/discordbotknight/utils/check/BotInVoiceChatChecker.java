package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**Check {@link com.reyzor.discordbotknight.utils.MessageUtil#checkBotVoiceChatConnection(MessageReceivedEvent)}
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public class BotInVoiceChatChecker extends Checker
{
    public BotInVoiceChatChecker() { super(); }
    public BotInVoiceChatChecker(Checker checker) { super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event)
    {
        if (!MessageUtil.checkBotVoiceChatConnection(event))
        {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
