package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
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
        final MessageChannel channel = event.getChannel();
        if (MessageUtil.checkMemberVoiceChatConnection(event))
        {
            if (MessageUtil.checkBotVoiceChatConnection(event))
            {
                final AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                if (handler != null && handler.getAudioPlayer() != null)
                {
                    final AudioTrack track = handler.getAudioPlayer().getPlayingTrack();
                    if (track != null)
                    {
                        StringBuilder sb = new StringBuilder("Продолжительность :\n");
                        sb.append(MessageUtil.formatTimeTrack(track.getDuration()));
                        sb.append("\n Автор ");
                        sb.append(track.getInfo().author);
                        EmbedBuilder builder = MessageUtil.getTemplateBuilder();
                        builder.setTitle("Текущий трек: ");
                        builder.addField("**" + track.getInfo().title + "** ", sb.toString(), false);
                        channel.sendMessage(builder.build()).queue();
                    } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_PLAY_TRACK.getMessage()).build()).queue();
                }
            } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return " - инфомация по текущему треку";
    }
}
