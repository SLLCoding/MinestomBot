package dev.sllcoding.minestombot.commands.impl;

import java.awt.Color;

import dev.sllcoding.minestombot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class Ping extends Command {
    EmbedBuilder eb = new EmbedBuilder();

    public Ping() {
        super("ping", "Gets bot ping", "Utilities");
    }

    @Override
    public void onCommand(Member member, Message message) {
        long time = System.currentTimeMillis();
        eb.setTitle("Pong!");
        eb.setDescription("Getting ping.");
        eb.setFooter(member.getEffectiveName(), member.getUser().getAvatarUrl());
        eb.setColor(Color.RED);
        message.getChannel().sendMessage(eb.build()).queue(response -> {
            eb.setTitle("Pong Complete!");
            eb.setDescription("Pong: " + (System.currentTimeMillis() - time) + " ms");
            eb.setColor(Color.GREEN);
            response.editMessage(eb.build()).queue();
        });
    }

}
