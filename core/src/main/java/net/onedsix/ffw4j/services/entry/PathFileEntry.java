package net.onedsix.ffw4j.services.entry;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

public class PathFileEntry extends AbstractFileEntry<Path> {
    public PathFileEntry(Path format) {
        super(format);
    }
    public PathFileEntry() {
        super();
    }
    
    @Override
    public Class<Path> targetClass() {
        return Path.class;
    }
    
    @Override
    public Function<Path, AbstractFileEntry<Path>> lambdaGet() {
        return PathFileEntry::new;
    }
    
    @Override
    public File toFile() {
        return this.format.toFile();
    }
    
    @Override
    public boolean isDirectory() {
        return toFile().isDirectory();
    }
}
