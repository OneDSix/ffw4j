package net.onedsix.ffw4j.core.entry;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;

public abstract class AbstractFileEntry<F> {
    //
    protected F format;
    
    //
    public AbstractFileEntry(F format) {
        this.format = format;
    }
    
    //
    public abstract File toFile();
    //
    public String toExtension() {
        return FilenameUtils.getExtension(toFile().getName());
    }
    //
    public Path toPath() {
        return toFile().toPath();
    }
}
