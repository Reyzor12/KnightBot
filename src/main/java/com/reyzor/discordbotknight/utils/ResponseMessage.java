package com.reyzor.discordbotknight.utils;

/**
 * Template response for all bot's command
 * */

public enum ResponseMessage
{
    USER_NOT_IN_VOICE_CHANNEL("Вы не состоите ни в одном голосовом чате"),
    USER_NOT_PERMISSION("У тебя тут нет силы!"),
    BOT_PERMISSION_JOIN_VOICE_CHANNEL("У бота нет прав на доспук к голосовому чату"),
    BOT_ALREADY_TRY_JOIN_CHANNEL("Бот уже пытается присоединиться к чату"),
    BOT_CONNECT_TO_VOICE_CHANNEL("Бот подключен к голосовому чату"),
    BOT_NOT_IN_VOICE_CHANNEL("Бот не в голосовом чате"),
    BOT_LEAVE_VOICE_CHANNEL("Бот покинул голосовой чат"),
    BOT_ON_PAUSE("Бот поставлен на паузу"),
    BOT_NOT_PLAY_TRACK("Бот не проигрывает ни какой трек сейчас"),
    BOT_CANT_USE_AUDIO("Бот не поддерживает аудио"),
    BOT_CANT_SET_AVATAR("Не удалось установить новую аватарку на бота"),
    BOT_SET_AVATAR("Новая аватарка успешно поставлена на бота"),
    NOT_CORRECT_LINK("Не корректно указана ссылка"),
    UNCORRECT_COMMAND_ARGS("Не корректные аргументы у данной команды"),
    TRACK_LIST_IS_EMPTY("Трек лист пуст"),
    UNABLE_CREATE_PLAYLIST_FILE("Не получилось создать файл для плейлиста!"),
    PLAYLIST_CREATE_WELL("Плейлист создан!"),
    PLAYLIST_ALREADY_EXIST("Такой плейлист уже существует!"),
    PLAYLIST_DOESNT_EXIST("Такого плейлиста не существует!"),
    UNABLE_DELETE_PLAYLIST_FILE("Не получается удалить файл плейлиста"),
    PLAYLIST_DELETE("Плейлист успешно удален"),
    PLAYLISTS_ARE_ABSENT("Нет плейлистов!"),
    UNABLE_CREATE_FOLDER_FOR_PLAYLIST("Не получается создать папку для плейлистов"),
    PLAYLISTS_NOT_AVAILABLE("Не удается загрузить плейлисты"),
    UNABLE_WRITE_TRACK_IN_PLAYLIST("Не удалось записать трек в плейлист"),
    FILE_WRITE_IN_PLAYLIST("Трек успешно записан в плейлист"),
    UNABLE_READ_FILE("Невозможно прочитать файл!");

    private String message;

    ResponseMessage(String message) { this.message = message; }


    public String getMessage() { return message; }
}
