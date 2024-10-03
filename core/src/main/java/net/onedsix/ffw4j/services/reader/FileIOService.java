package net.onedsix.ffw4j.services.reader;

import net.onedsix.ffw4j.core.entry.AbstractFileEntry;

import java.io.*;

public interface FileIOService<T> {
    //
    String getFileExtension();
    //
    T read(File file) throws Exception;
    //
    boolean write(File file, T data) throws Exception;
    //
    default T read(AbstractFileEntry<?> entry) throws Exception {
        return this.read(entry.toFile());
    }
    //
    default boolean write(AbstractFileEntry<?> entry, T data) throws Exception {
        return this.write(entry.toFile(), data);
    }
    
}
