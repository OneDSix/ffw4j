package net.onedsix.ffw4j.core.entry;

import java.io.File;
import java.nio.file.Path;

public class PathFileEntry extends AbstractFileEntry<Path> {
    public PathFileEntry(Path format) {
        super(format);
    }
    
    @Override
    public File toFile() {
        return this.format.toFile();
    }
}
