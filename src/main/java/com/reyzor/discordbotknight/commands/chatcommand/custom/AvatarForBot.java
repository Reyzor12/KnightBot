package com.reyzor.discordbotknight.commands.chatcommand.custom;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.SelfUser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Command for set avatar for bot {@link com.reyzor.discordbotknight.bots.Bot}
 * @author Reyzor
 * @version 1.0
 * @since 13.06.2018
 */

@Service("avatarForBot")
public class AvatarForBot extends DefaultChatCommand implements ChatCommandIF
{
    private static final String commandApply = "setavatar";

    @Autowired
    public AvatarForBot(Bot bot) {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        final SelfUser user = event.getGuild().getJDA().getSelfUser();
        final MessageChannel channel = event.getChannel();
        final List<String> args = getArgs(command);
        if (MessageUtil.checkPermission(event))
        {
            if (!args.isEmpty())
            {
                if (args.size() == 1) {
                    try {
                        user.getManager().setAvatar(Icon.from(MessageUtil.getSource(args.get(0))));
                        channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_SET_AVATAR.getMessage()).build()).queue();
                    } catch (IOException e) {
                        channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_CANT_SET_AVATAR.getMessage()).build()).queue();
                    }
                }else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.NOT_CORRECT_LINK.getMessage()).build()).queue();
            } else channel.sendMessage(MessageUtil.getInfoMessage("Не был указана ссылка на аватар бота").build()).queue();
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return " avatar_url - команда для установки аватара для бота";
    }
}
