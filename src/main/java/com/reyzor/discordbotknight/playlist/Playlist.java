package com.reyzor.discordbotknight.playlist;


import com.reyzor.discordbotknight.audio.AudioHandler;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Playlist for {@link com.reyzor.discordbotknight.bots.Bot}
 * @author reyzor
 * @version 1.0
 * @since 06.06.2018
 */
public class Playlist implements AudioPlaylist
{

    private final String name;
    private final List<String> items;
    private final boolean shuffle;
    private List<PlaylistLoadError> errors;
    private List<AudioTrack> tracks;
    private final static String TOO_LONG = "Этот трек слишком длинный";
    private final static String NO_MATCHES = "Совпадений не найдено";
    private final static String LOAD_FAIL = "Ошибка загрузки трека ";

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
                        if (AudioHandler.isTooLong(track))
                            errors.add(new PlaylistLoadError(index, items.get(index), TOO_LONG));
                        else
                        {
                            tracks.add(track);
                            consumer.accept(track);
                        }
                        if (last && callback != null) callback.run();
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist)
                    {
                        if (playlist.isSearchResult())
                        {
                            final AudioTrack firstTrack = playlist.getTracks().get(0);
                            if (AudioHandler.isTooLong(firstTrack))
                            {
                                errors.add(new PlaylistLoadError(index, items.get(index), TOO_LONG));
                            }
                            else
                            {
                                tracks.add(firstTrack);
                                consumer.accept(firstTrack);
                            }
                        }
                        else if (playlist.getSelectedTrack() != null)
                        {
                            final AudioTrack selectedTrack = playlist.getSelectedTrack();
                            if (AudioHandler.isTooLong(selectedTrack))
                            {
                                errors.add(new PlaylistLoadError(index, items.get(index), TOO_LONG));
                            }
                            else
                            {
                                tracks.add(selectedTrack);
                                consumer.accept(selectedTrack);
                            }
                        }
                        else
                        {
                            final List<AudioTrack> loadedTracks = new ArrayList<>(playlist.getTracks());
                            if (shuffle)
                            {
                                for (int first = 0; first < loadedTracks.size(); first++)
                                {
                                    int second = (int) Math.random()*loadedTracks.size();
                                    AudioTrack tmp = loadedTracks.get(first);
                                    loadedTracks.set(first, loadedTracks.get(second));
                                    loadedTracks.set(second, tmp);
                                }
                            }
                            loadedTracks.removeIf(track -> AudioHandler.isTooLong(track));
                            tracks.addAll(loadedTracks);
                            loadedTracks.stream().forEach(track -> consumer.accept(track));
                        }
                        if (last && callback != null) callback.run();
                    }

                    @Override
                    public void noMatches()
                    {
                        errors.add(new PlaylistLoadError(index, items.get(index), NO_MATCHES));
                        if (last && callback != null) callback.run();
                    }

                    @Override
                    public void loadFailed(FriendlyException exception)
                    {
                        errors.add(new PlaylistLoadError(index, items.get(index), LOAD_FAIL + exception));
                        if (last && callback != null) callback.run();
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
            return null;
        }
    }

    public static boolean folderExists()
    {
        return Files.exists(Paths.get("Playlist"));
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

    @Override
    public String getName() {
        return null;
    }

    public List<AudioTrack> getTracks() { return tracks; }

    @Override
    public AudioTrack getSelectedTrack() {
        return null;
    }

    @Override
    public boolean isSearchResult() {
        return false;
    }

    public static List<String> getPlaylists()
    {
        if (folderExists())
        {
            File folder = new File("Playlist");
            return Arrays.asList(folder.listFiles(pathname -> pathname.getName().endsWith(".txt")))
                    .stream()
                    .map(file -> file.getName().substring(0, file.getName().length() - 4))
                    .collect(Collectors.toList());
        }
        else
        {
            createFolder();
            return null;
        }
    }

    public class PlaylistLoadError
    {
        private final int index;
        private final String item;
        private final String reason;

        private PlaylistLoadError(Integer index, String item, String reason)
        {
            this.index = index;
            this.item = item;
            this.reason = reason;
        }

        public Integer getIndex() { return index; }
        public String getItem() { return item; }
        public String getReason() { return reason; }
    }
}
