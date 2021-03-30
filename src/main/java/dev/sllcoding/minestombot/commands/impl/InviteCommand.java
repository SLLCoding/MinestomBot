package dev.sllcoding.minestombot.commands.impl;

import dev.sllcoding.minestombot.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class InviteCommand extends Command {

    public InviteCommand() {
        super("invite", "Invite this bot to your server.", "Misc");
    }

    @Override
    public void onCommand(Member member, Message message) {

    }

}