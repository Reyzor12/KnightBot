package com.reyzor.discordbotknight.commands.chatcommand.channel;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class for execute operation to leave {@link com.reyzor.discordbotknight.bots.Bot} from
 * {@link net.dv8tion.jda.core.entities.VoiceChannel}
 * @author reyzor
 * @version 1.0
 * @since 06.06.2018
 */
@Service("leaveFromChatCommand")
public class LeaveFromChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static Logger logger = LoggerFactory.getLogger(LeaveFromChatCommand.class);

    private final static String commandApply = "leave";

    @Autowired
    public LeaveFromChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        if (!MessageUtil.checkPermission(event))
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
            return;
        }
        final VoiceChannel connectedChannel = MessageUtil.getVoiceChannel(event);
        if (!MessageUtil.checkMemberVoiceChatConnection(event))
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
            return;
        }
        if (!MessageUtil.checkBotVoiceChatConnection(event))
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
            return;
        }
        ((AudioHandler)event.getGuild().getAudioManager().getSendingHandler()).stopAndClear();
        event.getGuild().getAudioManager().closeAudioConnection();
        channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_LEAVE_VOICE_CHANNEL.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return "- покинуть голосовой чат";
    }
}
