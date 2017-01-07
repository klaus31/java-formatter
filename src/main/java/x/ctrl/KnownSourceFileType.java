package x.ctrl;

enum KnownSourceFileType {
    JAVA ( "java" ) , XML ( "xml" ) ;
    private final String suffix;
    KnownSourceFileType ( String suffix ) {
        this . suffix = suffix ;
    }
    String getSuffix() {
        return suffix;
    }
}