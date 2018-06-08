package com.reyzor.discordbotknight.commands;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ShowAllChatCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of interface {@link ClientCommand} and {@link EventListener}
 * realization of all actions, which {@link Bot} can perform
 * @author Reyzor
 * @version 1.0
 * @since 02.06.2018
 */

@Service("clientCommand")
public class ClientCommandImp implements EventListener, ClientCommand
{
    @Autowired
    private ShowAllChatCommand showAllChatCommand;

    private Bot bot;

    @Autowired
    public ClientCommandImp(Bot bot)
    {
        this.bot = bot;
    }

    @Override
    public void onEvent(Event event)
    {
        if (event instanceof MessageReceivedEvent)
        {
            this.onMessageReceived((MessageReceivedEvent) event);
        } else if (event instanceof GuildMessageDeleteEvent)
        {
            this.onMessageDelete((GuildMessageDeleteEvent) event);
        } else if (event instanceof GuildJoinEvent)
        {
            this.onGuildJoin((GuildJoinEvent) event);
        } else if (event instanceof GuildLeaveEvent)
        {
            this.onGuildLeave((GuildLeaveEvent) event);
        } else if (event instanceof ReadyEvent)
        {
            this.onReady((ReadyEvent) event);
        } else if (event instanceof ShutdownEvent)
        {
            this.onShutdown((ShutdownEvent) event);
        }

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (!isMessageFromBot(event))
        {
            if(isCommandForBot(event) && !bot.getCommand().isEmpty())
            {
                final String commandMessage = event.getMessage().getContentRaw().replace(bot.getBotConfig().getPrefix(),"");
                final String command = event.getMessage().getContentRaw().replace(bot.getBotConfig().getPrefix(),"").split(" ")[0];
                if (bot.getCommand().containsKey(command)) bot.getCommand().get(command).execute(event, commandMessage);
                else
                {
                    StringBuilder sb = new StringBuilder("Такой команды нет у данного бота!\n");
                    sb.append("посмотреть список команд можно введя команду ");
                    sb.append(bot.getBotConfig().getPrefix() + "list");
                    event.getChannel().sendMessage(sb.toString()).queue();
                }
            }
        }
    }

    @Override
    public void onMessageDelete(GuildMessageDeleteEvent event) {

    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {

    }

    @Override
    public void onReady(ReadyEvent event) {

    }

    @Override
    public void onShutdown(ShutdownEvent event) {

    }

    private boolean isMessageFromBot(MessageReceivedEvent event)
    {
        return event.getAuthor().isBot() ? true : false;
    }

    /**
     * Method get message from event, then check rawContent on present prefix {@link Bot:prefix} - command
     * @see MessageReceivedEvent
     * */

    private boolean isCommandForBot(MessageReceivedEvent event)
    {
        final Message message = event.getMessage();
        final String rawContent = message.getContentRaw();
        return (bot != null && rawContent.startsWith(bot.getBotConfig().getPrefix())) ? true : false;
    }
}
