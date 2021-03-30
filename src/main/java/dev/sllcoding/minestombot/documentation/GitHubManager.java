package dev.sllcoding.minestombot.documentation;

import dev.sllcoding.minestombot.MinestomBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GitHubManager {

    private static Git git;
    private static final File folder = new File("Minestom");

    public static void setup() throws IOException, GitAPIException {
        if (folder.exists()) {
            git = Git.open(folder);
        } else {
            git = Git.cloneRepository()
                    .setURI("https://github.com/Minestom/Minestom")
                    .setDirectory(folder)
                    .call();
        }
        MinestomBot.getBot().getPresence().setPresence(OnlineStatus.IDLE, Activity.watching("the data update."));
        update();
    }

    public static Git getGit() {
        return git;
    }

    public static File update() throws GitAPIException {
        git.pull().call();
        return folder;
    }

}
