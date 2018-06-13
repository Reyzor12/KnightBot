package com.reyzor.discordbotknight.commands.chatcommand;

import com.reyzor.discordbotknight.bots.Bot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Map;

/**
 * Show all commands in chat for {@link Bot}
 * @see DefaultChatCommand
 * @see ChatCommandIF
 * @author Reyzor
 * @version 1.0
 * @since 03.06.2018
 */

@Service
public class ShowAllChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "list";

    private Map<String, ChatCommandIF> commands;
    private EmbedBuilder builder;

    @Autowired
    public ShowAllChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        commands = bot.getCommand();
        if (builder == null)
        {
            builder = new EmbedBuilder();
            builder.setTitle("Список команд для чат бота KnightBot", null);
            builder.setColor(new Color(66, 244, 86));
            builder.setDescription("Для ввода аудио команд необходимо добавить бота в аудио канал." +
                    " Ввод всех команд начинается с " + bot.getBotConfig().getPrefix());
            commands
                    .keySet()
                    .stream()
                    .forEach(
                            currentCommand -> builder.addField(bot.getBotConfig().getPrefix() + currentCommand, commands.get(currentCommand).info(), false)
                    );
            builder.addBlankField(false);
            builder.setAuthor("Knight Bot", null, );

        }
        event.getChannel().sendMessage(builder.build()).queue();
    }

    public static String getCommandApply()
    {
        return commandApply;
    }

    @Override
    public String info() {
        return "- все команды для бота";
    }
}
