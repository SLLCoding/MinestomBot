package dev.sllcoding.minestombot;

import com.google.gson.JsonObject;
import dev.sllcoding.minestombot.commands.CommandManager;
import dev.sllcoding.minestombot.config.ConfigManager;
import dev.sllcoding.minestombot.documentation.GitHubManager;
import dev.sllcoding.minestombot.listeners.ListenerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class MinestomBot {

    private static JDA bot;

    public static void main(String[] args) throws LoginException, IOException, GitAPIException {
        JsonObject config = ConfigManager.setup();

        bot = JDABuilder.createDefault(config.get("token").getAsString()).build();

        bot.getPresence().setPresence(OnlineStatus.IDLE, Activity.watching("SLL facedesk."));

        GitHubManager.setup();
        CommandManager.setup();
        ListenerManager.setup();

        bot.getPresence().setPresence(OnlineStatus.ONLINE, Activity.watching("SLL facedesk."));
    }

    public static JDA getBot() {
        return bot;
    }

}
