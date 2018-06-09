package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Command for pause for playing music from {@link com.reyzor.discordbotknight.bots.Bot}
 * @author reyzor
 * @version 1.0
 * @since 09.06.2018
 */

@Service("pauseMusicChatCommand")
public class PauseMusicChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "pause";

    private Bot bot;

    @Autowired
    public PauseMusicChatCommand(Bot bot)
    {
        this.bot = bot;
        this.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        List<Permission> permissionList = event.getMember().getPermissions();
        MessageChannel channel = event.getChannel();
        if (permissionList.contains(Permission.ADMINISTRATOR) || permissionList.contains(Permission.MANAGE_CHANNEL)) {
            if (event.getMember().getVoiceState().getChannel() != null)
            {
                if (event.getGuild().getAudioManager().isConnected())
                {
                    AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                    if (handler != null && handler.getAudioPlayer().getPlayingTrack() != null && !handler.getAudioPlayer().isPaused())
                    {
                        handler.getAudioPlayer().setPaused(true);
                    }
                } else channel.sendMessage("Бот не подключен к голосовому чату").queue();
            }
            else channel.sendMessage("Вы не подключины к голосовому чату").queue();
        }
        else channel.sendMessage("У тебя тут нет силы!").queue();
    }

    @Override
    public String info()
    {
        return "поставить на паузу воспроизведения текущего трека";
    }
}
