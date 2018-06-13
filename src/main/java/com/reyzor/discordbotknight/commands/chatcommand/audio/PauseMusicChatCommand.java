package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Command for pause for playing music from {@link com.reyzor.discordbotknight.bots.Bot}
 * @author reyzor
 * @version 1.0
 * @since 09.06.2018
 */

@Service("pauseMusicChatCommand")
public class PauseMusicChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "pause";

    @Autowired
    public PauseMusicChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        if (MessageUtil.checkPermission(event)) {
            if (MessageUtil.checkMemberVoiceChatConnection(event))
            {
                if (MessageUtil.checkBotVoiceChatConnection(event))
                {
                    AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                    if (handler != null && handler.getAudioPlayer().getPlayingTrack() != null && !handler.getAudioPlayer().isPaused())
                    {
                        handler.getAudioPlayer().setPaused(true);
                        channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_ON_PAUSE.getMessage()).build()).queue();
                    }
                } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
            }
            else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
        }
        else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return "- поставить на паузу воспроизведения текущего трека";
    }
}
