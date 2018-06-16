package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.check.*;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Show current playing track {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack}
 * for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * @author Reyzor
 * @version 1.0
 * @since 10.06.2018
 */
@Service("currentTrackChatCommand")
public class CurrentTrackChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "track";

    @Autowired
    public CurrentTrackChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        Checker botPlayTrackChecker      = new BotPlayTrackChecker();
        Checker audioPlayerChecker       = new AudioPlayerChecker(botPlayTrackChecker);
        Checker handlerChecker           = new AudioHandlerChecker(audioPlayerChecker);
        Checker botInVoiceChatChecker    = new BotInVoiceChatChecker(handlerChecker);
        Checker memberInVoiceChatChecker = new CheckUserInVoiceChannelChecker(botInVoiceChatChecker);

        if (memberInVoiceChatChecker.check(event))
        {
            final MessageChannel channel = event.getChannel();
            final AudioTrack track = ((AudioHandler)event.getGuild().getAudioManager().getSendingHandler()).getAudioPlayer().getPlayingTrack();

            StringBuilder sb = new StringBuilder("Продолжительность :\n");
            sb.append(MessageUtil.formatTimeTrack(track.getDuration()));
            sb.append("\n Автор ");
            sb.append(track.getInfo().author);
            EmbedBuilder builder = MessageUtil.getTemplateBuilder();
            builder.setTitle("Текущий трек: ");
            builder.addField("**" + track.getInfo().title + "** ", sb.toString(), false);
            channel.sendMessage(builder.build()).queue();
        }
    }

    @Override
    public String info()
    {
        return " - инфомация по текущему треку";
    }
}
