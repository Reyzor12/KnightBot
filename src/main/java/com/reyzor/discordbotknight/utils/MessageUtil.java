package com.reyzor.discordbotknight.utils;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;

/**
 * Class for work with message form JDA
 * @author reyzor
 * @version 1.0
 * @since 08.06.2018
 */
public class MessageUtil
{
    private static Color leftBorderColor;
    private static EmbedBuilder builderMessage;

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

    /**
     * Get Color for Message left border
     * */

    public static Color getColorForLiftBorderMessage()
    {
        if (leftBorderColor == null) leftBorderColor = new Color(66, 244, 86);
        return leftBorderColor;
    }

    /**
     * default template for all message {@link net.dv8tion.jda.core.entities.Message}
     * in chat from bot {@link com.reyzor.discordbotknight.bots.Bot}
     * @return {@link EmbedBuilder}
     * */

    public static EmbedBuilder getTemplateBuilder()
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(getColorForLiftBorderMessage());
        builder.setAuthor("Knight Bot", null, LinkStorage.IDEA.getUrl());
        builder.setFooter(LinkStorage.TRADE_MARK, LinkStorage.AUTHOR.getUrl());
        builder.setThumbnail(LinkStorage.PICKACHU.getUrl());
        return builder;
    }

    /**
     *  Information from bot {@link com.reyzor.discordbotknight.bots.Bot}
     *  formed {@link EmbedBuilder} for message {@link net.dv8tion.jda.core.entities.Message}
     * @return {@link EmbedBuilder}
     * */

    public static EmbedBuilder getInfoMessage(String message)
    {
        if (builderMessage == null) builderMessage = getTemplateBuilder();
        builderMessage.setTitle(null);
        builderMessage.clearFields();
        builderMessage.addField(message,"",false);
        return builderMessage;
    }

    /**
     * Method get url, connect to site and return input stream of resource
     * @param url - string - url to resource at the Internet
     * @return {@link InputStream} - resource from Internet
     * */
    public static InputStream getSource(String url)
    {
        if (url == null) return null;
        try {
            URL iUrl = new URL(url);
            URLConnection connection = iUrl.openConnection();
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
