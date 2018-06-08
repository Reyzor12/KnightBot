package com.reyzor.discordbotknight.commands.chatcommand;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Base interface for all chat commands for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * Use chain pattern to link operations in chat
 * */

public interface ChatCommandIF {

    void execute(MessageReceivedEvent event, String command);
    String info();
    List<String> getArgs(String args);

    void setNext(ChatCommandIF commandIF);
    ChatCommandIF getNext();
    boolean hasNext();

    User getAuthor();
    Guild getGuild();
    List<String> getArgs();
}
