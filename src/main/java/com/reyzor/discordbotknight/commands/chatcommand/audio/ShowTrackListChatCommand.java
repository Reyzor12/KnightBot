package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.audio.QueueableTrack;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.reyzor.discordbotknight.utils.SpecificQueue;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
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
        final MessageChannel channel = event.getChannel();
        if (MessageUtil.checkMemberVoiceChatConnection(event))
        {
            if (MessageUtil.checkBotVoiceChatConnection(event))
            {
                final AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                if (handler != null)
                {
                    SpecificQueue<QueueableTrack> queue = handler.getQueue();
                    if (queue.isEmpty()) channel.sendMessage("Трек лист пуст!").queue();
                    else
                    {
                        StringBuilder sb = new StringBuilder("Список треков:\n");
                        int i = 0;
                        for (QueueableTrack track : queue.getAll())
                        {
                            i++;
                            sb.append(i);
                            sb.append(". - **");
                            sb.append(track.getTrack().getInfo().title);
                            sb.append("** продолжительность ");
                            sb.append(MessageUtil.formatTimeTrack(track.getTrack().getDuration()));
                            sb.append("\n");
                        }
                        channel.sendMessage(sb.toString()).queue();
                    }
                } else channel.sendMessage("Трек лист пуст!").queue();
            } else channel.sendMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).queue();
        } else channel.sendMessage(ResponseMessage.USER_NOT_IN_VOICE_CHANNEL.getMessage()).queue();
    }

    @Override
    public String info()
    {
        return " - показать все треки в плэй листе";
    }
}
