package x.java;

import x.ctrl.SourceCodeFile;
import x.java.snippets.*;
import java.nio.file.Path;
import java.util.*;
import static x.ctrl.MiserableLogger.logDebug;
public class JavaConfig {

    public static final String EOL = "\n";
    public static final String END_OF_FILE = EOL;
    private static Map<SourceCodeFile, IndentService> indentServices = new HashMap<>();

    private JavaConfig() {
    }

    public static IndentService getIndentService(SourceCodeFile file) {
        if (!indentServices.containsKey(file)) {
            indentServices.put(file, createIndentService());
        }
        return indentServices.get(file);
    }
    private static IndentService createIndentService() {
        return new IndentService("    ");
    }
    static CompilationUnit createCompilationUnit() {
        return new CompilationUnit();
    }

    public static final List<String> RULES_HAVING_A_MATCHING_FORMATTER = Arrays.asList("constructorDeclaration", "comment", "annotation", "packageDeclaration", "importDeclaration", "fieldDeclaration", "methodDeclaration", "interfaceMethodDeclaration", "classDeclaration", "interfaceDeclaration");
    public static final List<String> RULES_FORCE_STARTING_A_NEW_BLOCK = Arrays.asList("interfaceMethodModifier");

    static Optional<JavaCodeSnippet> getMatchingCodeSnippetFor(String ruleName) {
        logDebug("Using new CodeSnippet for " + ruleName);
        switch (ruleName) {
            case "annotation":
                return Optional.of(new Annotation());
            case "packageDeclaration":
                return Optional.of(new PackageDeclaration());
            case "importDeclaration":
                return Optional.of(new ImportDeclaration());
            case "comment":
                return Optional.of(new Comment());
            case "fieldDeclaration":
            case "methodDeclaration":
            case "interfaceMethodDeclaration":
            case "classDeclaration":
            case "constructorDeclaration":
            case "interfaceDeclaration":
                return Optional.of(new WhateverDeclaration());
            default:
                return Optional.empty();
        }
    }
}