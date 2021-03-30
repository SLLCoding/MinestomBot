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

public class JavadocCommand extends Command {

    public JavadocCommand() {
        super("javadoc", "Search the Minestom Javadoc", "Utilities");
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
                                if (clazz.getJavadocComment().isPresent())
                                    embedBuilder.setDescription(clazz.getJavadocComment().get().parse().getDescription().toText()
                                            .replaceAll("\\{@([^\\s]+) |}", "`").replaceAll("<([^\\s]+>)", "").replaceAll("\\r\\n|\\r|\\n", " "));
                                clazz.getMethods().forEach(method -> embedBuilder.addField(method.getNameAsString() + "(" + replaceLast(method.getParameters().toString().replaceFirst("\\[", ""), "]", "") + ")", method.getType().asString(), true));
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

    private static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

}
