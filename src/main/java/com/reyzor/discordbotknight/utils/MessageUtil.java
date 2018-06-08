package com.reyzor.discordbotknight.utils;

/**
 * @author reyzor
 * @version 1.0
 * @since 08.06.2018
 */
public class MessageUtil
{
    public static String formatTimeTrack(Long timeInLong)
    {
        if (timeInLong == Long.MAX_VALUE)
            return "LIVE ";
        long seconds = Math.round(timeInLong/1000.0);
        long hours = seconds/(3600);
        seconds %= 3600;
        long minutes = seconds/60;
        seconds %= 60;
        return (hours > 0 ? hours + ":" : "") + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }
}
