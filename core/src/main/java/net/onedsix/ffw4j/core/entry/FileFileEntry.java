package net.onedsix.ffw4j.core.entry;

import java.io.File;

public class FileFileEntry extends AbstractFileEntry<File> {
    public FileFileEntry(File format) {
        super(format);
    }
    
    @Override
    public File toFile() {
        return this.format;
    }
}
