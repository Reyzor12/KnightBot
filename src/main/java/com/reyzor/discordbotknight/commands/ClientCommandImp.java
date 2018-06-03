package com.reyzor.discordbotknight.commands;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
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

import java.util.Map;

/**
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
    private Map<String, ChatCommandIF> commands;

    public ClientCommandImp() {}

    @Autowired
    public ClientCommandImp(Bot bot)
    {
        this.bot = bot;
        this.commands = bot.getCommand();
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
                showAllChatCommand.execute(event, event.getMessage().getContentRaw());
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

    public Map<String, ChatCommandIF> getCommands()
    {
        return this.commands;
    }

    public void setCommands(Map<String, ChatCommandIF> commands)
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
