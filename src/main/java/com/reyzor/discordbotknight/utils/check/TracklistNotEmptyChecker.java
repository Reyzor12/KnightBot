package com.reyzor.discordbotknight.utils.check;

import com.reyzor.discordbotknight.audio.AudioHandler;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.reyzor.discordbotknight.utils.ResponseMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**Check playlist {@link com.reyzor.discordbotknight.playlist.Playlist}
 *  if empty show message {@link com.reyzor.discordbotknight.utils.ResponseMessage#TRACK_LIST_IS_EMPTY}
 *  else next checker {@link Checker}
 * @author Reyzor
 * @version 1.0
 * @since 16.06.2018
 */

public class TracklistNotEmptyChecker extends Checker
{
    public TracklistNotEmptyChecker() { super(); }
    public TracklistNotEmptyChecker(Checker checker) { super(checker); }

    @Override
    public boolean check(MessageReceivedEvent event) {
        if (((AudioHandler)event.getGuild().getAudioManager()).getQueue().isEmpty()) {
            event.getChannel().sendMessage(MessageUtil.getInfoMessage(ResponseMessage.TRACK_LIST_IS_EMPTY.getMessage()).build()).queue();
            return false;
        }
        return checkNext(event);
    }
}
