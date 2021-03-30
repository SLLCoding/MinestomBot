package dev.sllcoding.minestombot.utils;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class EmbedUtils {

    public static EmbedBuilder getDefaultEmbed() {
        return new EmbedBuilder()
                .setAuthor("Minestom Javadocs", "https://minestom.github.io/Minestom/", "https://gblobscdn.gitbook.com/spaces%2F-MJpMt9ABrjwz4C2P2ZK%2Favatar-1602920266287.png");
    }

    public static EmbedBuilder getUpdatingRepoEmbed() {
        return getDefaultEmbed()
                .setTitle("Updating repository")
                .setDescription("We'll be ready soon.")
                .setColor(Color.ORANGE);
    }

    public static EmbedBuilder getUpdatedRepoEmbed() {
        return getDefaultEmbed()
                .setTitle("Repository updated")
                .setDescription("Searching...")
                .setColor(Color.GREEN);
    }

    public static EmbedBuilder getFailedToUpdateRepoEmbed() {
        return getDefaultEmbed()
                .setTitle("Failed to update repository")
                .setDescription("Please try again later.")
                .setColor(Color.RED);
    }

    public static EmbedBuilder getNoResultsEmbed(String search) {
        return getDefaultEmbed()
                .setTitle("No results")
                .setDescription("There are no results for: `" + search + "`.")
                .setColor(Color.RED);
    }

    public static EmbedBuilder getInvalidArgumentsEmbed() {
        return getDefaultEmbed()
                .setTitle("Invalid Arguments")
                .setColor(Color.RED);
    }

    public static EmbedBuilder getErrorOccurredEmbed() {
        return getDefaultEmbed()
                .setTitle("An Error Occurred")
                .setDescription("This bot is still in development and bugs are scattered everywhere!")
                .setColor(Color.RED);
    }

    public static EmbedBuilder getGatheringDataEmbed() {
        return getDefaultEmbed()
                .setTitle("Gathering Data")
                .setDescription("This will take a few seconds.")
                .setColor(Color.ORANGE);
    }

}
