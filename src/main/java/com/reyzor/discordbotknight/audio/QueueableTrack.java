package com.reyzor.discordbotknight.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * Wrap for {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack} to queue it
 * @author reyzor
 * @version 1.0
 * @since 07.06.2018
 */
public class QueueableTrack implements  Queueable
{
    private final AudioTrack track;
    private final long owner;

    public QueueableTrack(AudioTrack track, Long owner)
    {
        this.track = track;
        this.owner = owner;
    }

    @Override
    public long getIdentifier()
    {
        return owner;
    }

    public AudioTrack getTrack()
    {
        return track;
    }
}
