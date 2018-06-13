package com.reyzor.discordbotknight.commands.chatcommand.custom;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Command for set name for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * @author reyzor
 * @version 1.0
 * @since 13.06.2018
 */
@Service("nameForBot")
public class NameForBot extends DefaultChatCommand implements ChatCommandIF
{
    private static final String commandApply = "setname";

    public NameForBot(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final List<String> args = getArgs(command);
        final MessageChannel channel = event.getChannel();
        if (MessageUtil.checkPermission(event))
        {
            if (!args.isEmpty())
            {
                event.getGuild().getJDA().getSelfUser().getManager().setName(String.join(" ", args)).complete();
                channel.sendMessage(MessageUtil.getInfoMessage("Ник бота был сменен на " + String.join(" ", args)).build()).queue();
            } else channel.sendMessage(MessageUtil.getInfoMessage("Не был введен ник для бота").build()).queue();
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return " name_of_bot - установить новое название для Knight бота";
    }
}
