package dev.sllcoding.minestombot.commands;

import dev.sllcoding.minestombot.MinestomBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandManager {

    private static final List<Command> commands = new ArrayList<>();

    public static void setup() {
        MinestomBot.getBot().getPresence().setPresence(OnlineStatus.IDLE, Activity.watching("the commands register."));
        Reflections reflections = new Reflections("dev.sllcoding.minestombot.commands.impl");
        Set<Class<? extends Command>> allCommands = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> command : allCommands) {
            try {
                commands.add(command.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                System.err.println("Invalid command: " + command.getSimpleName());
            }
        }
    }

    public static List<Command> getCommands() {
        return commands;
    }

}
