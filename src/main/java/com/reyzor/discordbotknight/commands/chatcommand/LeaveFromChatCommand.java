package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.utils.MessageUtil;
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
    private final static String notInVoiceChannel = "Бот не в голосовом канале";
    private final static String leaveVoiceChannel = "Бот покинул голосовой чат";

    @Autowired
    public LeaveFromChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final VoiceChannel connectedChannel = MessageUtil.getVoiceChannel(event);
        if (connectedChannel == null)
        {
            event.getChannel().sendMessage(notInVoiceChannel).queue();
            return;
        }
        ((AudioHandler)event.getGuild().getAudioManager().getSendingHandler()).stopAndClear();
        event.getGuild().getAudioManager().closeAudioConnection();
        event.getChannel().sendMessage(leaveVoiceChannel).queue();
    }

    @Override
    public String info()
    {
        return "- покинуть голосовой чат";
    }
}
