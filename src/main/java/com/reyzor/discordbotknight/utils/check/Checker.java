package com.reyzor.discordbotknight.utils.check;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Abstract class for check link condition for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * User pattern @chain pattern@
 * */

public abstract class Checker
{
    private Checker nextChecker;

    public Checker setNextChecker(Checker checker)
    {
        this.nextChecker = checker;
        return nextChecker;
    }

    public abstract boolean check(MessageReceivedEvent event);

    protected boolean checkNext(MessageReceivedEvent event)
    {
        if (nextChecker == null) {
            return true;
        }
        return nextChecker.check(event);
    }
}
