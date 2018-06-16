package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.audio.QueueableTrack;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.reyzor.discordbotknight.utils.SpecificQueue;
import com.reyzor.discordbotknight.utils.check.*;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Show all tracks {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack} at track list
 * {@link com.reyzor.discordbotknight.playlist.Playlist}
 * @author Reyzor
 * @version 1.0
 * @since 10.06.2018
 */
@Service("showTrackListChatCommand")
public class ShowTrackListChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "track_list";

    @Autowired
    public ShowTrackListChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply,this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        Checker playlistEmptyChecker = new TracklistNotEmptyChecker();
        Checker audioHandlerChecker = new AudioHandlerChecker(playlistEmptyChecker);
        Checker botInVoiceChannelChecker = new BotInVoiceChatChecker(audioHandlerChecker);
        Checker memberInVoiceChannelChecker = new CheckUserInVoiceChannelChecker(botInVoiceChannelChecker);

        final MessageChannel channel = event.getChannel();

        if (memberInVoiceChannelChecker.check(event))
        {
            SpecificQueue<QueueableTrack> queue = ((AudioHandler) event.getGuild().getAudioManager().getSendingHandler()).getQueue();
            EmbedBuilder builder = MessageUtil.getTemplateBuilder();
            builder.setTitle("Список треков: ");
            int i = 0;
            for (QueueableTrack track : queue.getAll())
            {
                i++;
                builder.addField(
                        i + ". - **" + track.getTrack().getInfo().title + "** ",
                        "продолжительность " + MessageUtil.formatTimeTrack(track.getTrack().getDuration()), true);
            }
            channel.sendMessage(builder.build()).queue();
        }
    }

    @Override
    public String info()
    {
        return " - показать все треки в плэй листе";
    }
}
