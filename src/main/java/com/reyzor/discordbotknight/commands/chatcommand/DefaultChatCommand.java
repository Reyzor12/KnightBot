package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default realization of interface {@link ChatCommandIF}
 * This abstract class is used for default realization common methods
 * of chat command
 * @author reyzor
 * @version 1.0
 * @since 05.06.2018
 */
public abstract class DefaultChatCommand implements ChatCommandIF
{
    protected ChatCommandIF nextCommand;
    protected MessageReceivedEvent event;
    protected Bot bot;

    public DefaultChatCommand(Bot bot)
    {
        this.bot = bot;
    }

    @Override
    public void setNext(ChatCommandIF nextCommand)
    {
        this.nextCommand = nextCommand;
    }

    @Override
    public ChatCommandIF getNext()
    {
        return nextCommand;
    }

    @Override
    public boolean hasNext()
    {
        return nextCommand != null;
    }

    @Override
    public List<String> getArgs(String command)
    {
        final String nCommand = command == null ? null : command.trim();
        final List<String> splitCommand = nCommand == null ? new ArrayList<>() : Arrays.asList(nCommand.split(" "));
        if (nCommand == null || nCommand.isEmpty() || splitCommand.size() < 2) return new ArrayList<>();
        return splitCommand.subList(1, splitCommand.size());
    }

    @Deprecated
    @Override
    public List<String> getArgs()
    {
        List<String> message = Arrays.asList(event.getMessage().getContentRaw().split(" "));
        return new ArrayList<>(message.subList(1, message.size()));
    }

    @Deprecated
    @Override
    public User getAuthor()
    {
        return event.getAuthor();
    }

    @Deprecated
    @Override
    public Guild getGuild()
    {
        return event.getGuild();
    }
}
