package com.reyzor.discordbotknight.commands.chatcommand.audio.subcommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Base command to playlist
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public interface PlaylistCommandIF
{
    void execute(MessageReceivedEvent event, List<String> command);
    String info();
}
