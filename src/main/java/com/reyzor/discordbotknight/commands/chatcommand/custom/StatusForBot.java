package com.reyzor.discordbotknight.commands.chatcommand.custom;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Set status for bot {@link com.reyzor.discordbotknight.bots.Bot}
 *
 * @author reyzor
 * @version 1.0
 * @since 14.06.2018
 */
@Service("statusForBot")
public class StatusForBot extends DefaultChatCommand implements ChatCommandIF
{
    private static final String commandApply = "setstatus";

    @Autowired
    public StatusForBot(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final MessageChannel channel = event.getChannel();
        final List<String> args = getArgs(command);
        if (MessageUtil.checkPermission(event))
        {
            if (args.isEmpty())
            {
                EmbedBuilder message = MessageUtil.getTemplateBuilder();
                message.setTitle("Список статусов бота");
                message.addField("online", " - онлайн", false);
                message.addField("idle"," - idle статус", false);
                message.addField("dnd", " - не беспокоить", false);
                message.addField("invisible", " - невидимый", false);
                message.addField("offline", " - оффлайн", false);
                channel.sendMessage(message.build()).queue();
            } else
            {
                if (args.size() < 2)
                {
                    String status = args.get(0);
                    bot.getBotConfig().setProperty("status", status);
                    event.getJDA().getPresence().setStatus(bot.getBotConfig().getStatus(status));
                    channel.sendMessage(MessageUtil.getInfoMessage("Статус бота изменен").build()).queue();
                } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
            }
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
    }

    @Override
    public String info() { return " - список статусов\nstatus_bot - установить новый статус для бота"; }
}
