package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.audio.ResultHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.check.*;
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
        Checker botInVoiceChannelChecker = new BotInVoiceChatChecker();
        Checker memberInVoiceChannelChecker = new CheckUserInVoiceChannelChecker(botInVoiceChannelChecker);
        Checker permissionChecker = new PermissionChecker(memberInVoiceChannelChecker);

        final MessageChannel channel = event.getChannel();

        if (permissionChecker.check(event))
        {
            super.event = event;
            final List<String> args = getArgs(command);

            if (args.isEmpty())
            {
                Checker audioPlayerOnPause = new AudioOnPauseChecker();
                Checker audioPlayerChecker = new AudioPlayerChecker(audioPlayerOnPause);
                Checker audioHandlerChecker = new AudioHandlerChecker(audioPlayerChecker);

                if (audioHandlerChecker.check(event))
                {
                    AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                    handler.getAudioPlayer().setPaused(false);
                    event.getChannel().sendMessage(MessageUtil.getInfoMessage("Продолжение **" + handler.getAudioPlayer().getPlayingTrack().getInfo().title + " **").build()).queue();
                    return;
                }
                StringBuilder sb = new StringBuilder("Не правильно введена команда \n");
                sb.append(bot.getBotConfig().getPrefix() + commandApply);
                sb.append(" youtube_url или \n");
                sb.append(bot.getBotConfig().getPrefix() + commandApply);
                sb.append(" чтобы продолжить воспроизведение");
                event.getChannel().sendMessage(MessageUtil.getInfoMessage(sb.toString()).build()).queue();
                return;
            }
            final String url = args.get(0);
            channel
                    .sendMessage(MessageUtil.getInfoMessage("Загрузка трека ** " + url + " **").build())
                    .queue(message -> bot.getAudioManager().loadItemOrdered(event.getGuild(), url, new ResultHandler(message, this, bot, false, null)));
        }
    }

    @Override
    public String info() {
        return "youtube_url - команда для воспроизведения музыки с youtube или без url для продолжения воспроизведения";
    }
}
