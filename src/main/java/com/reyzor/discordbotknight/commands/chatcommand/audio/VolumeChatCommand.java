package com.reyzor.discordbotknight.commands.chatcommand.audio;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.reyzor.discordbotknight.utils.check.Checker;
import com.reyzor.discordbotknight.utils.check.PermissionChecker;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** Set volume for {@link sun.security.provider.SeedGenerator.ThreadedSeedGenerator.BogusThread}
 * change file configuration which represented in {@link com.reyzor.discordbotknight.configuration.BotSettings}
 * command set new value and write it in config file
 * @author reyzor
 * @version 1.0
 * @since 15.06.2018
 */

@Service("volumeChatCommand")
public class VolumeChatCommand extends DefaultChatCommand implements ChatCommandIF
{
    private final static String commandApply = "volume";

    @Autowired
    public VolumeChatCommand(Bot bot)
    {
        super(bot);
        super.bot.addCommand(commandApply, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, String command)
    {
        Checker permissionChecker = new PermissionChecker();

        final MessageChannel channel = event.getChannel();

        if (permissionChecker.check(event)) {
            final List<String> args = getArgs(command);
            if (!args.isEmpty()) {
                if (!(args.size() > 1)) {
                    int newBotVolume;
                    try {
                        newBotVolume = Integer.parseInt(args.get(0));
                    } catch (NumberFormatException e) {
                        newBotVolume = -1;
                    }
                    if (newBotVolume < 0 || newBotVolume > 100) {
                        channel.sendMessage(MessageUtil.getInfoMessage("Громкость может быть задана в диапазоне от 0 до 100").build()).queue();
                    } else {
                        bot.setUpHandler(event.getGuild()).getAudioPlayer().setVolume(newBotVolume);
                        bot.setVolume(event.getGuild(), newBotVolume);
                        channel.sendMessage(MessageUtil.getInfoMessage("Громкость бота изменена и составляет " + newBotVolume + "%").build()).queue();
                    }
                } else
                    channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
            } else {
                final AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                int currentBotVolume = handler == null && bot.getBotSettings(event.getGuild()) == null ? 100 : (handler == null ? bot.getBotSettings(event.getGuild()).getVolume() : handler.getAudioPlayer().getVolume());
                channel.sendMessage(MessageUtil.getInfoMessage("Громкость бота " + currentBotVolume + "%").build()).queue();
            }
        }
    }

    @Override
    public String info()
    {
        return " целое_число - установка громкости бота от 0 до 100, команда без параметров выдаст текущее значение громкости бота";
    }
}
