package dev.sllcoding.minestombot.commands.impl;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import dev.sllcoding.minestombot.commands.Command;
import dev.sllcoding.minestombot.documentation.GitHubManager;
import dev.sllcoding.minestombot.documentation.JavadocParser;
import dev.sllcoding.minestombot.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.Comparator;
import java.util.List;

public class MethodsCommand extends Command {

    public MethodsCommand() {
        super("methods", "See the methods inside a class", "Utilities");
    }

    @Override
    public void onCommand(Member member, Message message) {
        String[] args = message.getContentRaw().split(" ");
        if (args.length > 1) {
            message.reply(EmbedUtils.getUpdatingRepoEmbed().build()).queue(reply -> {
                try {
                    GitHubManager.update();
                    reply.editMessage(EmbedUtils.getUpdatedRepoEmbed().build()).queue(replyAgain -> {
                        try {
                            List<ClassOrInterfaceDeclaration> results = JavadocParser.search(args[1]);
                            if (!results.isEmpty()) {
                                results.sort(Comparator.comparingInt(d -> StringUtils.getLevenshteinDistance(d.getNameAsString(), args[1])));
                                ClassOrInterfaceDeclaration clazz = results.get(0);
                                EmbedBuilder embedBuilder = EmbedUtils.getDefaultEmbed();
                                embedBuilder.setTitle(clazz.getNameAsString(), "https://minestom.github.io/Minestom/index-all.html#I:" + clazz.getNameAsString().split("")[0].toUpperCase());
                                StringBuilder builder = new StringBuilder();
                                clazz.getMethods().forEach(method -> {
                                    if (method.isAnnotationPresent(Deprecated.class)) {
                                        builder.append("**~~").append(method.getNameAsString()).append("~~** - ");
                                    } else {
                                        builder.append("**").append(method.getNameAsString()).append("** - ");
                                    }
                                    builder.append(method.getType().asString()).append("\n");
                                });
                                for (String string : justify(builder.toString(), 1024)) {
                                    embedBuilder.addField("", string, true);
                                }
                                replyAgain.editMessage(embedBuilder.build()).queue();
                            } else {
                                replyAgain.editMessage(EmbedUtils.getNoResultsEmbed(args[1]).build()).queue();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            reply.editMessage(EmbedUtils.getErrorOccurredEmbed().build()).queue();
                        }
                    });
                } catch (GitAPIException e) {
                    e.printStackTrace();
                    reply.editMessage(EmbedUtils.getFailedToUpdateRepoEmbed().build()).queue();
                }
            });
        } else {
            message.reply(EmbedUtils.getInvalidArgumentsEmbed().build()).queue();
        }
    }

    private String[] justify(String s, int limit) {
        StringBuilder justifiedText = new StringBuilder();
        StringBuilder justifiedLine = new StringBuilder();
        String[] words = s.split("\n");
        for (int i = 0; i < words.length; i++) {
            justifiedLine.append(words[i]).append("\n ");
            if (i+1 == words.length || justifiedLine.length() + words[i+1].length() > limit) {
                justifiedLine.deleteCharAt(justifiedLine.length() - 1);
                justifiedText.append(justifiedLine.toString()).append("jufidslhfusjdifld");
                justifiedLine = new StringBuilder();
            }
        }
        return justifiedText.toString().split("jufidslhfusjdifld");
    }

}
