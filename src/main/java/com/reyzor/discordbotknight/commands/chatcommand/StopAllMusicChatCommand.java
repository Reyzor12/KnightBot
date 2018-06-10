package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Chat command for bot {@link com.reyzor.discordbotknight.bots.Bot} to stop play
 * all tracks {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack}
 * Command delegate this task to {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager}
 * and clear all track list {@link com.reyzor.discordbotknight.playlist.Playlist}
 * set on pause {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayer}
 * Implements common interface for all chat command {@link ChatCommandIF} and have
 * default realization {@link DefaultChatCommand}
 * @author Reyzor
 * @version 1.0
 * @since 10.06.2018
 */

@Service("stopAllMusicChatCommand")
public class StopAllMusicChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "stop";

    @Autowired
    public StopAllMusicChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        if ()
    }

    @Override
    public String info()
    {
        return "- останавливает воспроизведения музыки и чистит трек лист";
    }
}
