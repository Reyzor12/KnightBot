package com.reyzor.discordbotknight.playlist;


import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Playlist for {@link com.reyzor.discordbotknight.bots.Bot}
 * @author reyzor
 * @version 1.0
 * @since 06.06.2018
 */
public class Playlist
{

    private final String name;
    private final List<String> items;
    private final boolean shuffle;
    private List<PlaylistLoadError> errors;
    private List<AudioTrack> tracks;

    private Playlist(String name, List<String> items, boolean shuffle)
    {
        this.name = name;
        this.items = items;
        this.shuffle = shuffle;
    }

    public void loadTracks(AudioPlayerManager manager, Consumer<AudioTrack> consumer, Runnable callback)
    {
        if (tracks == null)
        {
            tracks = new LinkedList<>();
            errors = new LinkedList<>();
            for (int i = 0; i < items.size(); i ++)
            {
                boolean last = i + 1 == items.size();
                int index = i;
                manager.loadItemOrdered(name, items.get(i), new AudioLoadResultHandler()
                {
                    @Override
                    public void trackLoaded(AudioTrack track)
                    {
                        if (AudioHandler)
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {

                    }

                    @Override
                    public void noMatches() {

                    }

                    @Override
                    public void loadFailed(FriendlyException exception) {

                    }
                });
            }
        }
    }

    public static Playlist loadPlaylist(String name)
    {
        try
        {
            if (folderExists())
            {
                boolean[] shuffle = {false};
                List<String> trackList = new ArrayList<>();
                Files.readAllLines(Paths.get("Playlist" + File.separator + name + ".txt"))
                        .forEach(str ->
                        {
                            String track = str.trim();
                            if (track.isEmpty()) return;
                            if (track.startsWith("#") || track.startsWith("//"))
                            {
                                track = track.replaceAll("\\s+", "");
                                if (track.equalsIgnoreCase("#shuffle") || track.equalsIgnoreCase("//shuffle"))
                                {
                                    shuffle[0] = true;
                                }
                            }
                            else trackList.add(track);
                        });
                if (shuffle[0])
                {
                    for (int first = 0; first < trackList.size(); first++)
                    {
                        int second = (int) (Math.random()*trackList.size());
                        String tmp = trackList.get(first);
                        trackList.set(first, trackList.get(second));
                        trackList.set(second, tmp);
                    }
                }
                return new Playlist(name, trackList, shuffle[0]);
            }
            else
            {
                createFolder();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean folderExists()
    {
        return Files.exists(Paths.get("Playlists"));
    }

    public static void createFolder()
    {
        try
        {
            Files.createDirectory(Paths.get("Playlist"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getItems() { return items; }

    public class PlaylistLoadError{}
}
