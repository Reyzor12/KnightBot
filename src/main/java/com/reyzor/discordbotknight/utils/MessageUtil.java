package com.reyzor.discordbotknight.utils;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Class for work with message form JDA
 * @author reyzor
 * @version 1.0
 * @since 08.06.2018
 */
public class MessageUtil
{
    /**
     * Function from long to String in format hours:minutes:seconds
     * */
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

    /**
     * @return true if bot has permission {@link Permission#VOICE_CONNECT}
     * */
    public static boolean hasPermissionToJoinVoiceChannel(MessageReceivedEvent event)
    {
        return event.getGuild().getSelfMember().hasPermission((TextChannel)event.getChannel(), Permission.VOICE_CONNECT);
    }

    /**
     * get voice channel from event
     * */

    public static VoiceChannel getVoiceChannel(MessageReceivedEvent event)
    {
        return event.getMember().getVoiceState().getChannel();
    }

    /**
     * Command for check permission to use command {@link com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF}     *
     * */

    public static boolean checkPermission (MessageReceivedEvent event)
    {
        final List<Permission> permissionList = event.getMember().getPermissions();
        return (permissionList.contains(Permission.ADMINISTRATOR) || permissionList.contains(Permission.MANAGE_CHANNEL)) ? true : false;
    }

    /**
     * Command for check connection to voice channel {@link VoiceChannel} for
     * user {@link net.dv8tion.jda.core.entities.User}
     * */

    public static boolean checkMemberVoiceChatConnection(MessageReceivedEvent event)
    {
        return event.getMember().getVoiceState().getChannel() == null ? false : true;
    }

    /**
     * Command for check connection to voice channel {@link VoiceChannel} for
     * bot {@link com.reyzor.discordbotknight.bots.Bot}
     * */

    public static boolean checkBotVoiceChatConnection(MessageReceivedEvent event)
    {
        return event.getGuild().getAudioManager().isConnected() ? true : false;
    }
}
