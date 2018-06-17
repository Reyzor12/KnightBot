package com.reyzor.discordbotknight.commands.chatcommand.audio.subcommand;

import com.reyzor.discordbotknight.commands.chatcommand.audio.PlaylistManagerChatCommand;
import com.reyzor.discordbotknight.playlist.Playlist;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Create playlist
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */
@Service("createPlaylistCommand")
public class CreatePlaylistCommand implements PlaylistCommandIF
{
    private static final String command = "create";

    public CreatePlaylistCommand()
    {
        PlaylistManagerChatCommand.addCommand(command, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, List<String> command)
    {
        final MessageChannel channel = event.getChannel();
        if (!command.isEmpty() && command.size() < 2)
        {
            if (Playlist.loadPlaylist(command.get(0)) == null)
            {
                try {
                    Files.createFile(Paths.get("Playlist" + File.separator + command.get(0) + ".txt"));
                    channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.PLAYLIST_CREATE_WELL.getMessage()).build()).queue();
                } catch (IOException e) {
                    channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNABLE_CREATE_PLAYLIST_FILE.getMessage()).build()).queue();
                }
            } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.PLAYLIST_ALREADY_EXIST.getMessage()).build()).queue();
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return command + " name_of_playlist - создать плейлист";
    }
}
