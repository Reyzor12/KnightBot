package com.reyzor.discordbotknight.commands.chatcommand.custom;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** Set game for {@link com.reyzor.discordbotknight.bots.Bot}
 * @author reyzor
 * @version 1.0
 * @since 14.06.2018
 */
@Service
public class GameForBot extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "setgame";

    @Autowired
    public GameForBot(Bot bot) {
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
            if (!args.isEmpty())
            {
                String game = String.join(" ", args);
                bot.getBotConfig().setProperty("game", game);
                event.getJDA().getPresence().setGame(Game.playing(game));
                channel.sendMessage(MessageUtil.getInfoMessage("Игра для бота изменена").build()).queue();
            } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
        } else channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.USER_NOT_PERMISSION.getMessage()).build()).queue();
    }

    @Override
    public String info()
    {
        return " name_game - название игры";
    }
}
