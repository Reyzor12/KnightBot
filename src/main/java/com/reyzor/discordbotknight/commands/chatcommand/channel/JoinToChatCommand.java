package com.reyzor.discordbotknight.commands.chatcommand.channel;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class for join {@link com.reyzor.discordbotknight.bots.Bot} to chat
 * @author Reyzor
 * @version 1.0
 * @since 05.06.2018
 */

@Service("joinToChatCommand")
public class JoinToChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "join";

    @Autowired
    public JoinToChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        if (!MessageUtil.checkPermission(event)) {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
            return;
        }
        //bot has permission to join voice channel
        if (!MessageUtil.hasPermissionToJoinVoiceChannel(event))
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_PERMISSION_JOIN_VOICE_CHANNEL.getMessage()).build()).queue();
            return;
        }
        //message from voice channel
        if (!MessageUtil.checkMemberVoiceChatConnection(event))
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_IN_VOICE_CHANNEL.getMessage()).build()).queue();
            return;
        }
        //audio enable
        if (!bot.getBotConfig().getAudioEnable())
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_CANT_USE_AUDIO.getMessage()).build()).queue();
            return;
        }
        final AudioManager audioManager = event.getGuild().getAudioManager();
        //try connect
        if (audioManager.isAttemptingToConnect())
        {
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_ALREADY_TRY_JOIN_CHANNEL.getMessage()).build()).queue();
            return;
        }
        //open audio connection
        audioManager.openAudioConnection(MessageUtil.getVoiceChannel(event));
        channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_CONNECT_TO_VOICE_CHANNEL.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return "- присоединить бота к каналу";
    }
}
