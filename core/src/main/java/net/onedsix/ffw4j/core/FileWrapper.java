package net.onedsix.ffw4j.core;

import net.onedsix.ffw4j.services.entry.AbstractFileEntry;
import net.onedsix.ffw4j.services.container.AbstractContainer;
import net.onedsix.ffw4j.services.reader.CastableFile;
import net.onedsix.ffw4j.services.reader.FileIOService;

import static net.onedsix.ffw4j.core.FFWServiceLoader.convertToFile;

/** The main class for FFW4J;<br>
 * contains {@link FileWrapper#read(Object)}, {@link FileWrapper#write(Object, AbstractContainer)},<br>
 * and {@link FileWrapper#convert(AbstractContainer, String)}. */
@SuppressWarnings("unused")
public class FileWrapper {
    //
    public static AbstractContainer read(Object file) throws FFWException {
        try {
            AbstractFileEntry<?> entry = convertToFile(file);
            String extension = entry.toExtension();
            return FFWServiceLoader.formatIOLookup.get(extension).read(entry.toFile());
        } catch (Exception e) {
            throw new FFWException("Issue whilst reading file!", e);
        }
    }
    
    //
    @SuppressWarnings("unchecked") // Result of generics being cast, should be fine
    public static boolean write(Object file, AbstractContainer data) throws FFWException {
        try {
            AbstractFileEntry<?> entry = convertToFile(file);
            String extension = entry.toExtension();
            return FFWServiceLoader.formatIOLookup.get(extension).write(entry.toFile(), data);
        } catch (Exception e) {
            throw new FFWException("Issue whilst writing file!", e);
        }
    }
    
    //
    public static <C> C readCast(Object file, Class<C> cast) throws FFWException {
        try {
            AbstractFileEntry<?> entry = convertToFile(file);
            String extension = entry.toExtension();
            FileIOService<?> ioService = FFWServiceLoader.formatIOLookup.get(extension);
            if (ioService instanceof CastableFile) return ((CastableFile) ioService).castRead(entry.toFile(), cast);
            else throw new ClassCastException("Could not cast; "+ioService.getClass().getSimpleName()+" does not implement CastableFile");
        } catch (Exception e) {
            throw new FFWException("Issue whilst reading file!", e);
        }
    }
    
    //
    public static <C> boolean writeCast(Object file, C data) throws FFWException {
        try {
            AbstractFileEntry<?> entry = convertToFile(file);
            String extension = entry.toExtension();
            FileIOService<?> ioService = FFWServiceLoader.formatIOLookup.get(extension);
            if (ioService instanceof CastableFile) return ((CastableFile) ioService).castWrite(entry.toFile(), data);
            else throw new ClassCastException("Could not cast; "+ioService.getClass().getSimpleName()+" does not implement CastableFile");
        } catch (Exception e) {
            throw new FFWException("Issue whilst writing file!", e);
        }
    }
    
    //
    @SuppressWarnings("unchecked") // Another result of generics being cast, should be fine
    public static <C extends AbstractContainer> AbstractContainer convert(
            C consumes, String to
    ) throws FFWException {
        try {
            return FFWServiceLoader.formatConversionLookup.get(consumes.getFileExtension(), to).convert(consumes);
        } catch (Exception e) {
            throw new FFWException("Issue whilst converting container!", e);
        }
    }
}
