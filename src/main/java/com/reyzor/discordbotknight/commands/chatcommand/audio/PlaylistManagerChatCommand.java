package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.commands.chatcommand.audio.subcommand.PlaylistCommandIF;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.reyzor.discordbotknight.utils.check.Checker;
import com.reyzor.discordbotknight.utils.check.PermissionChecker;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command for creating playlist {@link com.reyzor.discordbotknight.playlist.Playlist}
 * with audio tracks {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack}
 *
 * @author reyzor
 * @version 1.0
 * @since 15.06.2018
 */

@Service("playlistManagerChatCommand")
public class PlaylistManagerChatCommand extends DefaultChatCommand
{
    private final static String commandApply = "playlist";
    private static Map<String, PlaylistCommandIF> allPlaylistCommands;

    @Autowired
    public PlaylistManagerChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
        allPlaylistCommands = new HashMap<>();
    }

    public static void addCommand(String command, PlaylistCommandIF executor)
    {
        allPlaylistCommands.put(command, executor);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        Checker permissionChecker = new PermissionChecker();
        if (permissionChecker.check(event))
        {
            List<String> args = getArgs(command);
            if (!args.isEmpty())
            {
                final String baseCommand = args.remove(0);
                if (allPlaylistCommands.keySet().contains(baseCommand))
                {
                    allPlaylistCommands.get(baseCommand).execute(event, args);    
                } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
            } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
        }
    }

    @Override
    public String info()
    {
        StringBuilder sb = new StringBuilder();
        for (PlaylistCommandIF command : allPlaylistCommands.values()) sb.append(command.info() + "\n");
        return sb.toString();
    }
}
