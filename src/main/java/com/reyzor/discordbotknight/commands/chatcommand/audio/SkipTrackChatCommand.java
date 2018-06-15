package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

/**
 * Command for skip {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack} for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * in audio player {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayer}
 * @author reyzor
 * @version 1.0
 * @since 15.06.2018
 */

@Service("skipTrackChatCommand")
public class SkipTrackChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "skip";

    public SkipTrackChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        if (MessageUtil.checkPermission(event))
        {
            if (MessageUtil.checkBotVoiceChatConnection(event))
            {
                final AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                if (handler != null && handler.getAudioPlayer() != null) {
                    final AudioPlayer player = handler.getAudioPlayer();
                    final AudioTrack track = player.getPlayingTrack();
                    if (track != null)
                    {
                        player.stopTrack();
                        StringBuilder sb = new StringBuilder("Трек **");
                        sb.append(track.getInfo().title);
                        sb.append("** пропущен!");
                        channel.sendMessage(MessageUtil.getInfoMessage(sb.toString()).build()).queue();
                    } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_PLAY_TRACK.getMessage()).build()).queue();
                }
            } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return " - пропустить текущий трек";
    }
}
