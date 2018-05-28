package com.reyzor.discordbotknight.bots;

import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public interface Bot {
    Bot createBot();
    void startBot() throws LoginException;
    JDABuilder getJDABot();
}
