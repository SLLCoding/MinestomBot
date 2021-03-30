package dev.sllcoding.minestombot.listeners;

import dev.sllcoding.minestombot.MinestomBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ListenerManager {

    public static void setup() {
        MinestomBot.getBot().getPresence().setPresence(OnlineStatus.IDLE, Activity.watching("the listeners register."));
        Reflections reflections = new Reflections("dev.sllcoding.minestombot.listeners.impl");
        Set<Class<? extends ListenerAdapter>> allListeners = reflections.getSubTypesOf(ListenerAdapter.class);

        for (Class<? extends ListenerAdapter> listener : allListeners) {
            try {
                MinestomBot.getBot().addEventListener(listener.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                System.err.println("Invalid listener: " + listener.getSimpleName());
            }
        }
    }

}
