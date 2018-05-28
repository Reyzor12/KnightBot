package com.reyzor.discordbotknight.configuration;

/**
 * @author Reyzor
 * @version 1.0
 * @since 27.05.2018
 */

public class BotSettings
{
    public final static BotSettings DEFAULT_SETTINGS = new BotSettings(0l, 0l, 0l, 50, null, false);

    private long textId;
    private long voiceId;
    private long roleId;
    private int volume;
    private String defaultPlaylist;
    private boolean repeatMode;

    public BotSettings (String textId, String voiceId, String roleId, int volume, String defaultPlaylist, boolean repeatMode)
    {
        this.textId = setSettingFromStringToLong(textId);
        this.voiceId = setSettingFromStringToLong(voiceId);
        this.roleId = setSettingFromStringToLong(roleId);
        this.volume = volume;
        this.defaultPlaylist = defaultPlaylist;
        this.repeatMode = repeatMode;
    }

    public BotSettings (Long textId, Long voiceId, Long roleId, int volume, String defaultPlaylist, boolean repeatMode)
    {
        this.textId = textId;
        this.voiceId = voiceId;
        this.roleId = roleId;
        this.volume = volume;
        this.defaultPlaylist = defaultPlaylist;
        this.repeatMode = repeatMode;
    }

    private Long setSettingFromStringToLong(String setting)
    {
        try
        {
            return Long.parseLong(setting);
        } catch (NumberFormatException e)
        {
            return 0l;
        }
    }

    public long getTextId() {
        return textId;
    }

    public void setTextId(long textId) {
        this.textId = textId;
    }

    public long getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(long voiceId) {
        this.voiceId = voiceId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getDefaultPlaylist() {
        return defaultPlaylist;
    }

    public void setDefaultPlaylist(String defaultPlaylist) {
        this.defaultPlaylist = defaultPlaylist;
    }

    public boolean getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(boolean repeatMode) {
        this.repeatMode = repeatMode;
    }
}
