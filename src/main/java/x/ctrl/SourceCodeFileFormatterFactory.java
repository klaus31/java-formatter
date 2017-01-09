package x.ctrl;

import x.java.SourceCodeFileFormatter4JavaDefault;
import x.xml.SourceCodeFileFormatter4XmlDefault;
class SourceCodeFileFormatterFactory {
    public static SourceCodeFileFormatter get(KnownSourceFileType type, SourceCodeFile file) {
        switch (type) {
            case JAVA:
                return new SourceCodeFileFormatter4JavaDefault(file);
            case XML:
                return new SourceCodeFileFormatter4XmlDefault();
            default:
                throw new AssertionError("201612170736");
        }
    }
}