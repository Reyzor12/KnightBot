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
    BOT_NOT_PLAY_TRACK("Бот не проигрывает ни какоц трек сецчас"),
    BOT_CANT_USE_AUDIO("Бот не поддерживает аудио");

    private String message;

    ResponseMessage(String message) { this.message = message; }


    public String getMessage() { return message; }
}
