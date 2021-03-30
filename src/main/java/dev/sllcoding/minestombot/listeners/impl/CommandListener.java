package dev.sllcoding.minestombot.listeners.impl;

import dev.sllcoding.minestombot.commands.Command;
import dev.sllcoding.minestombot.commands.CommandManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args[0].startsWith("-")) {
            for (Command command : CommandManager.getCommands()) {
                String withoutDash = args[0].replace("-", "");
                if (withoutDash.equalsIgnoreCase(command.getName())) {
                    command.onCommand(event.getMember(), event.getMessage());
                }
            }
        }
    }

}
