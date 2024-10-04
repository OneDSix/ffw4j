package net.onedsix.ffw4j.gdx;

import com.badlogic.gdx.files.FileHandle;
import net.onedsix.ffw4j.services.entry.AbstractFileEntry;

import java.io.File;
import java.util.function.Function;

public class FileHandleEntry extends AbstractFileEntry<FileHandle> {
    public FileHandleEntry(FileHandle format) {
        super(format);
    }
    public FileHandleEntry() {
        super();
    }
    
    @Override
    public Class<FileHandle> targetClass() {
        return FileHandle.class;
    }
    
    @Override
    public Function<FileHandle, AbstractFileEntry<FileHandle>> lambdaGet() {
        return FileHandleEntry::new;
    }
    
    @Override
    public File toFile() {
        return this.format.file();
    }
    
    @Override
    public boolean isDirectory() {
        return format.isDirectory();
    }
}
