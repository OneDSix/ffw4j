package net.onedsix.ffw4j.services.entry;

import java.io.File;
import java.util.function.Function;

public class FileFileEntry extends AbstractFileEntry<File> {
    public FileFileEntry(File format) {
        super(format);
    }
    public FileFileEntry() {
        super();
    }
    
    @Override
    public Class<File> targetClass() {
        return File.class;
    }
    
    @Override
    public Function<File, AbstractFileEntry<File>> lambdaGet() {
        return FileFileEntry::new;
    }
    
    @Override
    public File toFile() {
        return this.format;
    }
    
    @Override
    public boolean isDirectory() {
        return format.isDirectory();
    }
}
