package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.audio.ResultHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.utils.MessageUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
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
        //check command from voice channel
        if (MessageUtil.getVoiceChannel(event) != null)
        {
            //check bot in voice chat
            if (event.getGuild().getAudioManager().isConnected())
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
                        boolean isDJ = event.getMember().hasPermission(Permission.MANAGE_SERVER);
                        if (!isDJ) event.getMember().getRoles().contains(event.getGuild().getRoleById(bot.getBotSettings(event.getGuild()).getRoleId()));
                        if (!isDJ) event.getMessage().editMessage("Не каждый лишь может добавлять музыку на сервер").queue();
                        else
                        {
                            handler.getAudioPlayer().setPaused(false);
                            event.getChannel().sendMessage("Продолжение " + handler.getAudioPlayer().getPlayingTrack().getInfo().title + " **").queue();
                        }
                        return;
                    }
                    StringBuilder sb = new StringBuilder("Не правильно введена команда \n");
                    sb.append(bot.getBotConfig().getPrefix() + commandApply + " ССЫЛКА_НА_ЮТУБ_МУЗЫКУ ");
                    event.getChannel().sendMessage(sb.toString()).queue();
                    return;
                }
                final String url = args.get(0);
                event
                        .getChannel()
                        .sendMessage("Загрузка трека ** " + url + " **")
                        .queue(message -> bot.getAudioManager().loadItemOrdered(event.getGuild(), url, new ResultHandler(message, this, bot, false)));
            } else event.getChannel().sendMessage("Бот не в голосовом чате!").queue();
        } else event.getChannel().sendMessage("Вы не в голосовом чате!").queue();
    }

    @Override
    public String info() {
        return "youtube_url - команда для воспроизведения музыки с youtube или без url для продолжения воспроизведения";
    }
}
