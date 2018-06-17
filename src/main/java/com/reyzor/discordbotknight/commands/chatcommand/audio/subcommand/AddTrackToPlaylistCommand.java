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
import java.nio.file.StandardOpenOption;
import java.util.List;

/**Command add {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack} to
 * {@link com.reyzor.discordbotknight.playlist.Playlist}
 * @author Reyzor
 * @version 1.0
 * @since 17.06.2018
 */
@Service("addTrackToPlaylistCommand")
public class AddTrackToPlaylistCommand implements PlaylistCommandIF
{
    private static final String command = "add";

    public AddTrackToPlaylistCommand()
    {
        PlaylistManagerChatCommand.addCommand(command, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, List<String> args)
    {
        final MessageChannel channel = event.getChannel();
        if (args.size() == 2)
        {
            String playlistName = args.get(0);
            String trackName = args.get(1)+"\n";
            Playlist playlist = Playlist.loadPlaylist(playlistName);
            if (playlist != null)
            {
                try
                {
                    Files.write(Paths.get("Playlist" + File.separator + playlistName + ".txt"), trackName.getBytes(), StandardOpenOption.APPEND);
                    channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.FILE_WRITE_IN_PLAYLIST.getMessage()).build()).queue();
                } catch (IOException e)
                {
                    channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNABLE_WRITE_TRACK_IN_PLAYLIST.getMessage()).build()).queue();
                }
            } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.PLAYLIST_DOESNT_EXIST.getMessage()).build()).queue();
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return command + " name_of_playlist name_of_track - добавить трек в плейлист";
    }
}
