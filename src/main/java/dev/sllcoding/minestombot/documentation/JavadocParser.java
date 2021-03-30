package dev.sllcoding.minestombot.documentation;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JavadocParser {

    private static final File folder = new File("Minestom");

    public static void setup() {

    }

    public static List<ClassOrInterfaceDeclaration> search(String clazz) {
        List<ClassOrInterfaceDeclaration> results = new ArrayList<>();
        FilenameFilter filter = (file, name) -> file.isFile() && name.equalsIgnoreCase(clazz.toLowerCase() + ".java");
        try (Stream<Path> walk = Files.walk(folder.toPath())) {
            walk.filter(path -> {
                File file = path.toFile();
                return filter.accept(file, file.getName());
            }).forEach(path -> {
                File file = path.toFile();
                try {
                    new VoidVisitorAdapter<Object>() {
                        @Override
                        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                            super.visit(n, arg);
                            results.add(n);
                        }
                    }.visit(StaticJavaParser.parse(file), null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}
