package com.reyzor.discordbotknight.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;

/**
 * Base command for bot
 * @author Reyzor
 * @version 1.0
 * @since 27.05.2018
 */

@Service
public class BaseCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String message = event.getMessage().getContentDisplay();
        if (message.contains("!"))
        {
            String command = message.substring(1);
            if (command.equals("play"))
            {
                System.out.println("Hello world");
            }
        }
    }
}
