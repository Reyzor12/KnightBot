package com.reyzor.discordbotknight.commands.chatcommand.channel;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.reyzor.discordbotknight.utils.check.*;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
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
        Checker connectAudioChecker     = new BotAlreadyTryConnectChecker();
        Checker audioEnableAudioChecker = new BotAudioChecker(bot, connectAudioChecker);
        Checker memberInVoiceChecker    = new CheckUserInVoiceChannelChecker(audioEnableAudioChecker);
        Checker permissionToJoinChecker = new PermissionToJoinVoiceChannelChecker(memberInVoiceChecker);
        Checker permissionChecker       = new PermissionChecker(permissionToJoinChecker);

        final MessageChannel channel = event.getChannel();

        if (permissionChecker.check(event))
        {
            event.getGuild().getAudioManager().openAudioConnection(MessageUtil.getVoiceChannel(event));
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_CONNECT_TO_VOICE_CHANNEL.getMessage()).build()).queue();
        }
    }

    @Override
    public String info()
    {
        return "- присоединить бота к каналу";
    }
}
