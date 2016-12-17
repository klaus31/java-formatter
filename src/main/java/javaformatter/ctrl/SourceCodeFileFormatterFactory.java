package javaformatter.ctrl;

import javaformatter.java.SourceCodeFileFormatter4JavaDefault;
import javaformatter.xml.SourceCodeFileFormatter4XmlDefault;

class SourceCodeFileFormatterFactory {
    public static SourceCodeFileFormatter get(KnownSourceFileType type) {
        switch (type) {
            case JAVA:
                return new SourceCodeFileFormatter4JavaDefault();
            case XML:
                return new SourceCodeFileFormatter4XmlDefault();
            default:
                throw new AssertionError("201612170736");
        }
    }
}
