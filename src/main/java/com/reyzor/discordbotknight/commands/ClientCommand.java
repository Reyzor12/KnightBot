package com.reyzor.discordbotknight.commands;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;

/**
 * Interface for all {@link net.dv8tion.jda.core.hooks.EventListener}
 * which bot can handle
 * @author reyzor
 * @since 05.06.2018
 * @version 1.0
 * */

public interface ClientCommand
{
    void onMessageReceived(MessageReceivedEvent event);
    void onMessageDelete(GuildMessageDeleteEvent event);
    void onGuildJoin(GuildJoinEvent event);
    void onGuildLeave(GuildLeaveEvent event);
    void onReady(ReadyEvent event);
    void onShutdown(ShutdownEvent event);
}
