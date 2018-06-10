package com.reyzor.discordbotknight.audio;

import com.reyzor.discordbotknight.bots.Bot;
import com.reyzor.discordbotknight.commands.chatcommand.ChatCommandIF;
import com.reyzor.discordbotknight.utils.MessageUtil;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Message;

/**
 * Class implements interface {@link AudioLoadResultHandler} - Handles the result of loading an item from an audio player manager
 * @author reyzor
 * @version 1.0
 * @since 08.06.2018
 */

public class ResultHandler implements AudioLoadResultHandler
{
    final private Message message;
    final private ChatCommandIF command;
    final private boolean youtubeSearch;
    final private Bot bot;

    public ResultHandler(Message message, ChatCommandIF command,Bot bot, Boolean youtubeSearch)
    {
        this.message = message;
        this.command = command;
        this.youtubeSearch = youtubeSearch;
        this.bot = bot;
    }

    @Override
    public void trackLoaded(AudioTrack track)
    {
        loadSingleTrack(track, null);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist)
    {
        if (playlist.getTracks().size() == 1 || playlist.isSearchResult())
        {
            AudioTrack single = playlist.getSelectedTrack() == null ? playlist.getTracks().get(0) : playlist.getSelectedTrack();
            loadSingleTrack(single, null);
        } else if (playlist.getSelectedTrack() != null)
        {
            AudioTrack single = playlist.getSelectedTrack();
            loadSingleTrack(single, playlist);
        } else
        {
            int count = loadPlaylist(playlist, null);
            if (count == 0)
            {
                StringBuilder sb = new StringBuilder("Все вхождения в трек лист **");
                sb.append(playlist.getName() == null ? "" : "(**" + playlist.getName() + "**) ");
                sb.append("привышают максимум (`");
                sb.append(MessageUtil.formatTimeTrack(AudioHandler.MAX_SECONDS*1000) + "`)");
                message.editMessage(sb.toString()).queue();
            } else
            {
                StringBuilder sb = new StringBuilder("Найден ");
                sb.append(playlist.getName() == null ? "плейлист с`" : "плейлист (**" + playlist.getName() + "**) с `");
                sb.append(playlist.getTracks().size());
                sb.append("` треками, трек успешно добавлен");
                message.editMessage(sb.toString()).queue();
            }
        }
    }

    @Override
    public void noMatches()
    {
        if (youtubeSearch)
        {
            message.editMessage("Ничего по запросу найти не удалось").queue();
        }
        else
        {
            bot.getAudioManager().loadItemOrdered(command.getGuild(), "youtube поиск " + command.getArgs(), new ResultHandler(message, command, bot, true));
        }
    }

    @Override
    public void loadFailed(FriendlyException exception)
    {
        message.editMessage("Не получилось загрузить трек").queue();
    }

    private void loadSingleTrack(AudioTrack track, AudioPlaylist playlist)
    {
        if (AudioHandler.isTooLong(track))
        {
            StringBuilder sb = new StringBuilder("Трек **");
            sb.append(track.getInfo().title);
            sb.append("**) слишком длинный ");
            sb.append(MessageUtil.formatTimeTrack(track.getDuration()));
            sb.append(" > ");
            sb.append(MessageUtil.formatTimeTrack(AudioHandler.MAX_SECONDS*1000));
            sb.append(" ¯ \\ _ (ツ) _ / ¯ ");
            message.editMessage(sb.toString()).queue();
            return;
        }
        int pos = bot.queueTrack(command, track) + 1;
        StringBuilder sb = new StringBuilder(command.getAuthor().getName());
        sb.append(" Добавил **");
        sb.append(track.getInfo().title);
        sb.append("** {`");
        sb.append(MessageUtil.formatTimeTrack(track.getDuration()));
        sb.append("}` ");
        sb.append((pos == 0 ? "начало воспроизведения ..." : "на позицию " + pos +" в трек лист "));
        if (playlist == null) message.editMessage(sb.toString()).queue();
        else
        {
            loadPlaylist(playlist, track);
        }
    }

    private int loadPlaylist(AudioPlaylist playlist, AudioTrack track)
    {
        int[] count = {0};
        playlist.getTracks().stream().forEach(cTrack ->
        {
            bot.queueTrack(command, cTrack);
            count[0]++;
        });
        return count[0];
    }
}
