package net.onedsix.ffw4j.core;

import net.onedsix.ffw4j.core.entry.AbstractFileEntry;
import net.onedsix.ffw4j.core.entry.EntryDispatcher;
import net.onedsix.ffw4j.services.container.AbstractFormatContainer;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

/** The main class for FFW4J;<br>
 * contains {@link FileWrapper#read(Object)}, {@link FileWrapper#write(Object, AbstractFormatContainer)},<br>
 * and {@link FileWrapper#convert(AbstractFormatContainer, String)}. */
public class FileWrapper {
    
    @ApiStatus.Internal
    public static AbstractFileEntry<?> convertToFile(Object obj) throws FFWException {
        Function<Object, AbstractFileEntry<?>> converter = EntryDispatcher.get(obj.getClass());
        if (converter != null) {
            return converter.apply(obj);
        } else {
            throw new FFWException("This is not a convertible type!");
        }
    }
    
    public static AbstractFormatContainer read(Object file) throws FFWException {
        try {
            AbstractFileEntry<?> entry = convertToFile(file);
            String extension = entry.toExtension();
            return (AbstractFormatContainer) FFWServiceLoader.formatIOLookup.get(extension).read(entry.toFile());
        } catch (Exception e) {
            throw new FFWException("Issue whilst reading file!", e);
        }
    }
    
    public static boolean write(Object file, AbstractFormatContainer data) throws FFWException {
        try {
            AbstractFileEntry<?> entry = convertToFile(file);
            String extension = entry.toExtension();
            return FFWServiceLoader.formatIOLookup.get(extension).write(entry.toFile(), data);
        } catch (Exception e) {
            throw new FFWException("Issue whilst writing file!", e);
        }
    }
    
    public static <C extends AbstractFormatContainer, T extends AbstractFormatContainer> T
            convert(C consumes, String to) throws FFWException {
        try {
            return (T) FFWServiceLoader.formatConversionLookup.get(consumes.getFileExtension(), to).convert(consumes);
        } catch (Exception e) {
            throw new FFWException("Issue whilst converting container!", e);
        }
    }
}
