package com.reyzor.discordbotknight.commands.chatcommand.audio.subcommand;

import com.reyzor.discordbotknight.commands.chatcommand.audio.PlaylistManagerChatCommand;
import com.reyzor.discordbotknight.playlist.Playlist;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Show all playlists {@link com.reyzor.discordbotknight.playlist.Playlist}
 * @author Reyzor
 * @version 1.0
 * @since 17.06.2018
 */

@Service("allPlaylistCommand")
public class AllPlaylistCommand implements PlaylistCommandIF
{
    private static final String command = "all";

    public AllPlaylistCommand()
    {
        PlaylistManagerChatCommand.addCommand(command, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, List<String> command)
    {
        final MessageChannel channel = event.getChannel();
        if (!Playlist.folderExists())
        {
            Playlist.createFolder();
        }
        if (!Playlist.folderExists())
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNABLE_CREATE_FOLDER_FOR_PLAYLIST.getMessage()).build()).queue();
            return;
        }
        List<String> allPlaylists = Playlist.getPlaylists();
        if (allPlaylists == null) channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.PLAYLISTS_NOT_AVAILABLE.getMessage()).build()).queue();
        else if (allPlaylists.isEmpty()) channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.PLAYLISTS_ARE_ABSENT.getMessage()).build()).queue();
        else
        {
            EmbedBuilder builder = MessageUtil.getTemplateBuilder();
            builder.setTitle("Список плейлистов:");
            allPlaylists.stream().forEach(playlist -> builder.addField("`" + playlist + "`", "", false));
            channel.sendMessage(builder.build()).queue();
        }

    }

    @Override
    public String info()
    {
        return command + " - показать все плейлисты";
    }
}
