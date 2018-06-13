package com.reyzor.discordbotknight.utils;

/** Class for keeping link for all images and gif
 * @author reyzor
 * @version 1.0
 * @since 13.06.2018
 */
public enum LinkStorage
{
    PICKACHU("https://raw.githubusercontent.com/Reyzor12/KnightBot/develop/src/main/resources/icons/pokemon.png"),
    IDEA("https://raw.githubusercontent.com/Reyzor12/KnightBot/develop/src/main/resources/icons/idea.png");

    public static final String TRADE_MARK = "Reyzor\u2122";

    private String url;

    LinkStorage(String url)
    {
        this.url = url;
    }

    public String getUrl() { return url; }
}
