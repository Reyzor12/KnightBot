package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.audio.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/** Check present {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayer}
 * at {@link net.dv8tion.jda.core.entities.AudioChannel}
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public class AudioPlayerChecker extends Checker
{
    public AudioPlayerChecker() { super(); }
    public AudioPlayerChecker(Checker checker) { super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event)
    {
        if (((AudioHandler)event.getGuild().getAudioManager()).getAudioPlayer() == null) return false;
        return checkNext(event);
    }
}
