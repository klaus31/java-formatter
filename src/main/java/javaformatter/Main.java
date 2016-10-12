package javaformatter;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(final String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage java -jar formatter.jar <directory-with-java-sources>");
            System.exit(1610121904);
        }
        JavaSources javaSources = new JavaSources(Paths.get(args[0]));
        javaSources.forEach((javaSource) -> {
            System.out.println("\n\n---------------------------------------------------");
            System.out.println(javaSource.getPath().getFileName());
            System.out.println("---------------------------------------------------\n\n");
            SourceConfig sourceConfig = new SourceConfig();
            JavaSourceFormatter formatter = new JavaSourceFormatter(javaSource, sourceConfig);
            try {
                formatter.format();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1610122032);
            }
            formatter.withSource(System.out::println);
            System.out.println("\n\n---------------------------------------------------\n\n");
        });
    }


}
