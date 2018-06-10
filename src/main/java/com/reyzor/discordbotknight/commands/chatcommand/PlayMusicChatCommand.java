package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.audio.ResultHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Command for start play playlist from chat command
 * @author reyzor
 * @version 1.0
 * @since 06.06.2018
 */
@Service("playMusicChatCommand")
public class PlayMusicChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private static final Logger logger = LoggerFactory.getLogger(PlayMusicChatCommand.class);

    private static final String commandApply = "play";

    @Autowired
    public PlayMusicChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        if (MessageUtil.checkPermission(event)) {
            //check command from voice channel
            if (MessageUtil.checkMemberVoiceChatConnection(event))
            {
                //check bot in voice chat
                if (MessageUtil.checkBotVoiceChatConnection(event))
                {
                    super.event = event;
                    final List<String> args = getArgs(command);
                    //command don't have argument
                    if (args.isEmpty())
                    {
                        //set AudioHandler
                        //audioManager setup in class BaseBot method setUpHandler
                        AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                        //if method setUpHandler invoke early and track playing and its on pause
                        //when check permission and start play
                        if (handler != null && handler.getAudioPlayer().getPlayingTrack() != null && handler.getAudioPlayer().isPaused())
                        {
                            handler.getAudioPlayer().setPaused(false);
                            event.getChannel().sendMessage("Продолжение **" + handler.getAudioPlayer().getPlayingTrack().getInfo().title + " **").queue();
                            return;
                        }
                        StringBuilder sb = new StringBuilder("Не правильно введена команда \n");
                        sb.append(bot.getBotConfig().getPrefix() + commandApply);
                        sb.append(" youtube_url или \n");
                        sb.append(bot.getBotConfig().getPrefix() + commandApply);
                        sb.append(" чтобы продолжить воспроизведение");
                        event.getChannel().sendMessage(sb.toString()).queue();
                        return;
                    }
                    final String url = args.get(0);
                    channel
                            .sendMessage("Загрузка трека ** " + url + " **")
                            .queue(message -> bot.getAudioManager().loadItemOrdered(event.getGuild(), url, new ResultHandler(message, this, bot, false)));
                } else channel.sendMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).queue();
            } else channel.sendMessage(ResponseMessage.USER_NOT_IN_VOICE_CHANNEL.getMessage()).queue();
        } else channel.sendMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).queue();
    }

    @Override
    public String info() {
        return "youtube_url - команда для воспроизведения музыки с youtube или без url для продолжения воспроизведения";
    }
}
