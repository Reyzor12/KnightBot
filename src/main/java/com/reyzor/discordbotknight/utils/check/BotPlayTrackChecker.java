package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/** Check playing track {@link javafx.scene.media.AudioTrack} use
 * {@link com.sedmelluq.discord.lavaplayer.player.AudioPlayer}
 * at the bot {@link com.reyzor.discordbotknight.bots.Bot}
 * in channel {@link net.dv8tion.jda.core.entities.VoiceChannel}
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public class BotPlayTrackChecker extends Checker
{
    public BotPlayTrackChecker() { super(); }
    public BotPlayTrackChecker(Checker checker) { super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event)
    {
        if (((AudioHandler) event.getGuild().getAudioManager().getSendingHandler()).getAudioPlayer().getPlayingTrack() == null)
        {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.BOT_NOT_PLAY_TRACK.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
