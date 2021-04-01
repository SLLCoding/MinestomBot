package dev.sllcoding.minestombot.commands.impl;

import java.awt.Color;

import dev.sllcoding.minestombot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class Ping extends Command {
    EmbedBuilder eb = new EmbedBuilder();

    public Ping() {
        super("Ping", "Gets bot ping", "Utilities");
    }

    @Override
    public void onCommand(Member member, Message message) {
        long time = System.currentTimeMillis();
        eb.setTitle("Pong!", "https://gblobscdn.gitbook.com/spaces%2F-MJpMt9ABrjwz4C2P2ZK%2Favatar-1602920266287.png");
        eb.setDescription("Getting ping.");
        eb.setFooter(member.getEffectiveName(), member.getUser().getAvatarUrl());
        eb.setColor(Color.RED);
        message.getChannel().sendMessage(eb.build()).queue(response -> {
            eb.setTitle("Pong Complete!", "https://gblobscdn.gitbook.com/spaces%2F-MJpMt9ABrjwz4C2P2ZK%2Favatar-1602920266287.png");
            eb.setDescription("Pong: " + (System.currentTimeMillis() - time) + " ms");
            eb.setColor(Color.GREEN);
            response.editMessage(eb.build()).queue();
        });
    }

}
