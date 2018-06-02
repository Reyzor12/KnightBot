package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

/**
 * @author Reyzor
 * @version 1.0
 * @since 03.06.2018
 */

@Service
public class ShowAllChatCommand implements ChatCommandIF
{
    private Bot bot;
    private MessageReceivedEvent event;

    public ShowAllChatCommand(Bot bot, MessageReceivedEvent event)
    {
        this.bot = bot;
        this.event = event;
    }

    @Override
    public void onEvent(Event event) {

    }
}
