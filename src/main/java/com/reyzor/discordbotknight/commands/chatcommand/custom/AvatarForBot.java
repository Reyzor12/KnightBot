package com.reyzor.discordbotknight.commands.chatcommand.custom;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

/**
 * Command for set avatar for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * @author reyzor
 * @version 1.0
 * @since 13.06.2018
 */
@Service
public class AvatarForBot extends DefaultChatCommand implements ChatCommandIF
{
    private static final String commandApply = "setavatar";

    public AvatarForBot(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {

    }

    @Override
    public String info()
    {
        return " url_of_avatar - установить новый аватар для Knight бота";
    }
}
