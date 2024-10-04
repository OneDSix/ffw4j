package net.onedsix.ffw4j.services.entry;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

public abstract class AbstractFileEntry<F> {
    //
    protected F format;
    
    //
    public AbstractFileEntry(F format) {
        this.format = format;
        if (this.isDirectory()) throw new IllegalArgumentException("Can not pass a directory into a file entry");
    }
    public AbstractFileEntry() {}
    
    //
    public abstract Class<F> targetClass();
    //
    public abstract Function<F, AbstractFileEntry<F>> lambdaGet();
    //
    public abstract File toFile();
    //
    public abstract boolean isDirectory();
    //
    public  String toExtension() {
        return FilenameUtils.getExtension(toFile().getName());
    }
    //
    public Path toPath() {
        return toFile().toPath();
    }
}
