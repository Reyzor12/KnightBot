package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Command for start play playlist from chat command
 * @author reyzor
 * @version 1.0
 * @since 06.06.2018
 */
@Service("playMusicChatCommand")
public class PlayMusicChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private static final Logger logger = LoggerFactory.getLogger(PlayMusicChatCommand.class);

    private static final String commandApply = "play";

    private Bot bot;

    @Autowired
    public PlayMusicChatCommand(Bot bot)
    {
        this.bot = bot;
        this.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final String url = command.substring(commandApply.length()+1);

    }

    @Override
    public String info() {
        return "команда для воспроизведения музыки";
    }
}
