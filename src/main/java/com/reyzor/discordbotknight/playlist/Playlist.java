package com.reyzor.discordbotknight.playlist;

import java.util.List;

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
    //private List<PlaylistLoadError>

    private Playlist(String name, List<String> items, boolean shuffle)
    {
        this.name = name;
        this.items = items;
        this.shuffle = shuffle;
    }

}
