package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Reyzor
 * @version 1.0
 * @since 03.06.2018
 */

@Service
public class ShowAllChatCommand implements ChatCommandIF
{
    private final static String commandApply = "commands";
    private Map<String, ChatCommandIF> commands;
    private ChatCommandIF nextCommand;
    private String response;
    private Bot bot;

    @Autowired
    public ShowAllChatCommand(Bot bot)
    {
        this.bot = bot;
        this.bot.addCommand(commandApply, this);
    }


    public void setNext(ChatCommandIF nextCommand)
    {
        this.nextCommand = nextCommand;
    }

    public void execute(MessageReceivedEvent event, String command)
    {
        commands = bot.addCommand(commandApply, this);
        if (response == null)
        {
            StringBuilder sb = new StringBuilder("Список команд для KnightBot:\n");
            commands
                    .keySet()
                    .stream()
                    .forEach(
                            currentCommand -> sb
                                    .append(bot.getBotConfig().getPrefix() + currentCommand + " - " + commands.get(currentCommand).info())
                    );
            response = sb.toString();
        }

        event.getChannel().sendMessage(response).queue();
    }

    @Override
    public String info() {
        return "все команды для бота";
    }
}
