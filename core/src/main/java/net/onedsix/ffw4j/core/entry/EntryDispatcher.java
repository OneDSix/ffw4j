package net.onedsix.ffw4j.core.entry;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.ZipEntry;

public class EntryDispatcher {
    
    private static final Map<Class<?>, Function<Object, AbstractFileEntry<?>>> converters = new HashMap<>();
    
    public static Function<Object, AbstractFileEntry<?>> get(Class<?> key) {
        return converters.get(key);
    }
    
    static {
        // This might be ported to a service as well
        converters.put(File.class, obj -> new FileFileEntry((File) obj));
        converters.put(FileHandle.class, obj -> new FileHandleEntry((FileHandle) obj));
        converters.put(Path.class, obj -> new PathFileEntry((Path) obj));
        converters.put(String.class, obj -> new StringFileEntry((String) obj));
        converters.put(ZipEntry.class, obj -> new ZipEntryEntry((ZipEntry) obj));
    }
}
