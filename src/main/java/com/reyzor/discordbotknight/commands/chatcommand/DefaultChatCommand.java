package com.reyzor.discordbotknight.commands.chatcommand;

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
}
