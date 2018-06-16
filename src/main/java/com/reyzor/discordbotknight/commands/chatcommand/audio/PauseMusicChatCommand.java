package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.reyzor.discordbotknight.utils.check.*;
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
        Checker audioNotOnPauseChecker      = new AudioNotOnPauseChecker();
        Checker audioPlayerChecker          = new AudioPlayerChecker(audioNotOnPauseChecker);
        Checker audioHandlerChecker         = new AudioHandlerChecker(audioPlayerChecker);
        Checker botInVoiceChannelChecker    = new BotInVoiceChatChecker(audioHandlerChecker);
        Checker memberInVoiceChannelChecker = new CheckUserInVoiceChannelChecker(botInVoiceChannelChecker);
        Checker permissionChecker           = new PermissionChecker(memberInVoiceChannelChecker);

        final MessageChannel channel = event.getChannel();
        final AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();

        if (permissionChecker.check(event))
        {
            handler.getAudioPlayer().setPaused(true);
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_ON_PAUSE.getMessage()).build()).queue();
        }
    }

    @Override
    public String info()
    {
        return "- поставить на паузу воспроизведения текущего трека";
    }
}
