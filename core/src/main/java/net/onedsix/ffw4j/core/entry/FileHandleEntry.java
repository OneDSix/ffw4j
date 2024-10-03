package net.onedsix.ffw4j.core.entry;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public class FileHandleEntry extends AbstractFileEntry<FileHandle> {
    public FileHandleEntry(FileHandle format) {
        super(format);
    }
    
    @Override
    public File toFile() {
        return this.format.file();
    }
}
