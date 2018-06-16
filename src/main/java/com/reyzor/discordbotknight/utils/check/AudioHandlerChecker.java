package com.reyzor.discordbotknight.utils.check;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Check {@link com.reyzor.discordbotknight.audio.AudioHandler} in
 * {@link net.dv8tion.jda.core.entities.AudioChannel}
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public class AudioHandlerChecker extends Checker
{
    public AudioHandlerChecker(){ super(); }
    public AudioHandlerChecker(Checker checker){ super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event) {
        if (event.getGuild().getAudioManager() == null) return false;
        return checkNext(event);
    }
}
