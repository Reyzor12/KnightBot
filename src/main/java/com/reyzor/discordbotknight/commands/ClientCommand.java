package com.reyzor.discordbotknight.commands;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;

public interface ClientCommand
{
    void onMessageReceived(MessageReceivedEvent event);
    void onMessageDelete(GuildMessageDeleteEvent event);
    void onGuildJoin(GuildJoinEvent event);
    void onGuildLeave(GuildLeaveEvent event);
    void onReady(ReadyEvent event);
    void onShutdown(ShutdownEvent event);
}
