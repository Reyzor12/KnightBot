package com.reyzor.discordbotknight.commands.chatcommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface ChatCommandIF {
    void execute(MessageReceivedEvent event, String command);
    String info();
}
