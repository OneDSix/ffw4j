package net.onedsix.ffw4j.services.entry;

import java.io.File;
import java.util.function.Function;

public class StringFileEntry extends AbstractFileEntry<String> {
    public StringFileEntry(String format) {
        super(format);
    }
    public StringFileEntry() {
        super();
    }
    
    @Override
    public Class<String> targetClass() {
        return String.class;
    }
    
    @Override
    public Function<String, AbstractFileEntry<String>> lambdaGet() {
        return StringFileEntry::new;
    }
    
    @Override
    public File toFile() {
        return new File(this.format);
    }
    
    @Override
    public boolean isDirectory() {
        return toFile().isDirectory();
    }
}
