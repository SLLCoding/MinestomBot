package dev.sllcoding.minestombot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public abstract class Command {

    private final String name;
    private final String description;
    private final String category;

    public Command(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public abstract void onCommand(Member member, Message message);

}
