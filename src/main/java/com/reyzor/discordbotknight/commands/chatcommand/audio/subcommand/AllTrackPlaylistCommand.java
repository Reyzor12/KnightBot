package com.reyzor.discordbotknight.commands.chatcommand.audio.subcommand;

import com.reyzor.discordbotknight.commands.chatcommand.audio.PlaylistManagerChatCommand;
import com.reyzor.discordbotknight.playlist.Playlist;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**Show all {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack} from
 * {@link com.reyzor.discordbotknight.playlist.Playlist}
 * @author Reyzor
 * @version 1.0
 * @since 18.06.2018
 */
@Service("allTrackPlaylistCommand")
public class AllTrackPlaylistCommand implements PlaylistCommandIF
{
    private static final String command = "tracks";

    public AllTrackPlaylistCommand()
    {
        PlaylistManagerChatCommand.addCommand(command, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, List<String> command)
    {
        final MessageChannel channel = event.getChannel();
        if (command.size() == 1)
        {
            if (!Playlist.folderExists())
            {
                Playlist.createFolder();
            }
            if (!Playlist.folderExists())
            {
                channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNABLE_CREATE_FOLDER_FOR_PLAYLIST.getMessage()).build()).queue();
                return;
            }
            try
            {
                EmbedBuilder message = MessageUtil.getTemplateBuilder();
                message.setTitle("Список треков:");
                List<String> tracks = Files.readAllLines(Paths.get("Playlist" + File.separator + command.get(0) + ".txt"));
                tracks.stream().forEach(track -> message.addField(track, "", false));
                channel.sendMessage(message.build()).queue();
            } catch (IOException e) {
                channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNABLE_READ_FILE.getMessage()).build()).queue();
            }

        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();

    }

    @Override
    public String info()
    {
        return command + " name_of_playlist - показать список всех треков";
    }
}
