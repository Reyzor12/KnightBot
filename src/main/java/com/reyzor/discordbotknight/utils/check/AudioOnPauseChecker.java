package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.audio.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Check if {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayer}
 * on pause
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public class AudioOnPauseChecker extends Checker
{
    public AudioOnPauseChecker() { super(); }
    public AudioOnPauseChecker(Checker checker) { super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event) {
        if (!((AudioHandler)event.getGuild().getAudioManager().getSendingHandler()).getAudioPlayer().isPaused()) return false;
        return checkNext(event);
    }
}
