package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
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
    private final static String voicePermission = "У бота нет прав на доспук к голосовому чату";
    private final static String commandNotFromVoiceChat = "Вы не состоите ни в одном голосовом чате";
    private final static String botIsTryingToConnect = "Бот уже пытается присоединиться к чату";
    private final static String botConnectedToVoiceChannel = "Бот подключен к голосовому чату";
    private final static String botNotSupportedAudio = "Бот не поддерживает аудио";

    private Bot bot;

    @Autowired
    public JoinToChatCommand(Bot bot)
    {
        this.bot = bot;
        this.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        if (!hasPermissionToJoinVoiceChannel(event))
        {
            event.getChannel().sendMessage(voicePermission).queue();
            return;
        }
        final VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
        if (voiceChannel == null)
        {
            event.getChannel().sendMessage(commandNotFromVoiceChat).queue();
            return;
        }
        if (!bot.getBotConfig().getAudioEnable())
        {
            event.getChannel().sendMessage(botNotSupportedAudio).queue();
        }
        final AudioManager audioManager = event.getGuild().getAudioManager();
        if (audioManager.isAttemptingToConnect())
        {
            event.getChannel().sendMessage(botIsTryingToConnect).queue();
            return;
        }
        audioManager.openAudioConnection(voiceChannel);
        event.getChannel().sendMessage(botConnectedToVoiceChannel).queue();
    }

    @Override
    public String info()
    {
        return "присоединить бота к каналу";
    }

    /**
     * Check permission for join to voice channel
     * */

    public boolean hasPermissionToJoinVoiceChannel(MessageReceivedEvent event)
    {
        return event.getGuild().getSelfMember().hasPermission((TextChannel)event.getChannel(), Permission.VOICE_CONNECT);
    }
}
