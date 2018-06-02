package com.reyzor.discordbotknight.commands;

import com.reyzor.discordbotknight.bots.Bot;
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

import java.util.Map;

/**
 * @author Reyzor
 * @version 1.0
 * @since 02.06.2018
 */

@Service("clientCommand")
public class ClientCommandImp implements EventListener, ClientCommand
{
    private Bot bot;
    private Map<String, EventListener> commands;

    public ClientCommandImp() {}

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
            if(isCommandForBot(event) && !commands.isEmpty())
            {
                if ()
                /*StringBuilder sb = new StringBuilder(event.getChannel().getName());
                sb.append(" ");
                sb.append(event.getChannelType().name());
                sb.append(" ");
                sb.append(event.getGuild().getName());
                sb.append(" ");
                sb.append(event.getMessage().getContentRaw());
                event.getMessage().getChannel().sendMessage(sb.toString()).queue();*/
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

    private boolean isCommandForBot(MessageReceivedEvent event)
    {
        final Message message = event.getMessage();
        final String rawContent = message.getContentRaw();
        if (bot != null && rawContent.startsWith(bot.getBotConfig().getPrefix()))
        {
            return true;
        }
        return false;
    }

    public Map<String, EventListener> getCommands()
    {
        return this.commands;
    }

    public void setCommands(Map<String, EventListener> commands)
    {
        if (this.commands != null)
        {
            this.commands.clear();
            this.commands.putAll(commands);
        } else {
            this.commands = commands;
        }
    }
}
