package com.reyzor.discordbotknight.audio;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.playlist.Playlist;
import com.reyzor.discordbotknight.utils.SpecificQueue;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Audio service for play tracks and some help stuff
 * @author reyzor
 * @version 1.0
 * @since 07.06.2018
 */

public class AudioHandler extends AudioEventAdapter implements AudioSendHandler
{
    private AudioPlayer audioPlayer;
    private Bot bot;
    private Guild guild;
    private Long userId;
    private final SpecificQueue<QueueableTrack> queue;
    private final List<AudioTrack> defaultQueue;
    private AudioFrame lastFrame;
    public static boolean STAY_IN_CHANNEL;
    public static long MAX_SECONDS = -1;


    public AudioHandler(AudioPlayer audioPlayer, Bot bot, Guild guild)
    {
        this.audioPlayer = audioPlayer;
        this.bot = bot;
        this.guild = guild;
        this.queue = new SpecificQueue<>();
        this.defaultQueue = new LinkedList<>();
    }

    /**
     * Add {@link AudioTrack} from user {@link User} to queue
     * {@link SpecificQueue}
     * @param track - {@link AudioTrack} to add
     * @param user - from whom
     * @return index of inserting in queue
     * */

    public int addTrack(AudioTrack track, User user)
    {
        if (audioPlayer.getPlayingTrack() == null)
        {
            userId = user.getIdLong();
            audioPlayer.playTrack(track);
            return -1;
        } else
        {
            return queue.add(new QueueableTrack(track, user.getIdLong()));
        }
    }

    public SpecificQueue<QueueableTrack> getQueue() { return queue; }

    public void stopAndClear()
    {
        queue.clear();
        defaultQueue.clear();
        audioPlayer.stopTrack();
    }

    public boolean isMusicPlaying()
    {
        return guild.getSelfMember().getVoiceState().inVoiceChannel() && audioPlayer.getPlayingTrack() != null;
    }

    public AudioPlayer getAudioPlayer()
    {
        return audioPlayer;
    }

    public Long getUserId()
    {
        return userId;
    }

    public boolean playFromDefault()
    {
        if (!defaultQueue.isEmpty())
        {
            audioPlayer.playTrack(defaultQueue.remove(0));
            return true;
        }
        if (bot.getBotSettings(guild) == null || bot.getBotSettings(guild).getDefaultPlaylist() == null)
        {
            return false;
        }
        final Playlist tempPlaylist = Playlist.loadPlaylist(bot.getBotSettings(guild).getDefaultPlaylist());
        if (tempPlaylist == null || tempPlaylist.getItems().isEmpty()) return false;
        tempPlaylist.loadTracks(bot.getAudioManager(), at ->
        {
            if (audioPlayer.getPlayingTrack() == null) audioPlayer.playTrack(at);
            else defaultQueue.add(at);
        }, () ->
        {
            if (tempPlaylist.getTracks().isEmpty() && !STAY_IN_CHANNEL) guild.getAudioManager().closeAudioConnection();
        });
        return true;
    }

    public static boolean isTooLong(AudioTrack track)
    {
        return MAX_SECONDS <= 0 ? false : Math.round(track.getDuration()/1000.0) > MAX_SECONDS;
    }

    @Override
    public boolean canProvide() {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public byte[] provide20MsAudio() {
        return lastFrame.data;
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
