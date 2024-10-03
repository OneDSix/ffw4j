package net.onedsix.ffw4j.core.entry;

import java.io.File;

public class StringFileEntry extends AbstractFileEntry<String> {
    public StringFileEntry(String format) {
        super(format);
    }
    
    @Override
    public File toFile() {
        return new File(this.format);
    }
}
