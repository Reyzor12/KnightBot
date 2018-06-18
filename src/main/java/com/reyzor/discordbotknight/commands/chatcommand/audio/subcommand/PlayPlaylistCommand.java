package com.reyzor.discordbotknight.commands.chatcommand.audio.subcommand;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.audio.ResultHandler;
import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.commands.chatcommand.DefaultChatCommand;
import com.reyzor.discordbotknight.commands.chatcommand.audio.PlaylistManagerChatCommand;
import com.reyzor.discordbotknight.playlist.Playlist;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import com.reyzor.discordbotknight.utils.check.BotInVoiceChatChecker;
import com.reyzor.discordbotknight.utils.check.CheckUserInVoiceChannelChecker;
import com.reyzor.discordbotknight.utils.check.Checker;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** Play all {@link com.sedmelluq.discord.lavaplayer.track.AudioTrack} from
 * {@link com.reyzor.discordbotknight.playlist.Playlist}
 * @author reyzor
 * @version 1.0
 * @since 18.06.2018
 */
@Service("playPlaylistCommand")
public class PlayPlaylistCommand extends DefaultChatCommand implements PlaylistCommandIF, ChatCommandIF {
    private static final String command = "play";

    private Bot bot;

    @Autowired
    public PlayPlaylistCommand(Bot bot)
    {
        super(bot);
        this.bot = bot;
        PlaylistManagerChatCommand.addCommand(command, this);
    }

    @Override
    public void execute(MessageReceivedEvent event, List<String> command)
    {

        Checker botInVoiceChannelChecker = new BotInVoiceChatChecker();
        Checker memberInVoiceChannelChecker = new CheckUserInVoiceChannelChecker(botInVoiceChannelChecker);

        final MessageChannel channel = event.getChannel();

        if (memberInVoiceChannelChecker.check(event)) {
            if (command.size() == 1) {
                AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
                if (handler == null) handler = bot.setUpHandler(event.getGuild());
                final AudioPlayer audioPlayer = handler.getAudioPlayer();
                if (audioPlayer.getPlayingTrack() != null) handler.stopAndClear();

                final Playlist playlist = Playlist.loadPlaylist(command.get(0));

                if (playlist != null) {
                    final List<String> tracks = playlist.getItems();

                    if (tracks != null && !tracks.isEmpty()) {

                        this.event = event;
                        AudioHandler finalHandler = handler;
                        channel
                                .sendMessage(MessageUtil.getInfoMessage("Начинает проигрываться плейлист @" + command.get(0) + "@").build())
                                .queue(message -> tracks.stream().forEach(track -> bot.getAudioManager().loadItemOrdered(event.getGuild(), track, new ResultHandler(message, this, bot, false, playlist))));
                    }
                } else
                    channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.TRACK_LIST_IS_EMPTY.getMessage()).build()).queue();
            } else
                channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.PLAYLIST_DOESNT_EXIST.getMessage()).build()).queue();
        } else
            channel.sendMessage(MessageUtil.getInfoMessage(ResponseMessage.UNCORRECT_COMMAND_ARGS.getMessage()).build()).queue();
    }



    @Override
    public void execute(MessageReceivedEvent event, String command) {
    }

    @Override
    public String info()
    {
        return command + " name_of_playlist - начать воспроизведения плейлиста";
    }
}
