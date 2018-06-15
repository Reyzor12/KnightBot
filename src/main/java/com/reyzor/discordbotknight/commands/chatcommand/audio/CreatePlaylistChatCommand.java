package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

/**
 * Command for creating playlist {@link com.reyzor.discordbotknight.playlist.Playlist}
 * with audio tracks {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack}
 *
 * @author reyzor
 * @version 1.0
 * @since 15.06.2018
 */

@Service("createPlaylistChatCommand")
public class CreatePlaylistChatCommand extends DefaultChatCommand
{
    private final static String commandApply = "cplaylist";

    public CreatePlaylistChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        if ()
    }

    @Override
    public String info()
    {
        return " name_of_playlist - команда для создания плейлиста";
    }
}
