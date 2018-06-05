package com.reyzor.discordbotknight.commands.chatcommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Base interface for all chat commands for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * Use chain pattern to link operations in chat
 * */

public interface ChatCommandIF {

    void execute(MessageReceivedEvent event, String command);
    String info();

    void setNext(ChatCommandIF commandIF);
    ChatCommandIF getNext();
    boolean hasNext();
}
