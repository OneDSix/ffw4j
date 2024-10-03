package net.onedsix.ffw4j.core;

import lombok.extern.slf4j.Slf4j;
import net.onedsix.ffw4j.services.conversion.FileConversionService;
import net.onedsix.ffw4j.services.reader.FileIOService;
import net.onedsix.ratils.trimap.TriMap;
import net.onedsix.ratils.trimap.hash.HashTriMap;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/** The entrypoint to FFW4J, loading in all of the needed classes and plugins, allowing for FFW4J to do its job.<br>
 * <br>
 * */
@SuppressWarnings({"rawtypes","unused"})
@Slf4j
public class FFWServiceLoader {
    
    private static boolean loaded = false;
    public static boolean isLoaded() {
        return loaded;
    }
    
    //
    static final Map<String, FileIOService> formatIOLookup = new HashMap<>();
    //
    static final TriMap<String, String, FileConversionService> formatConversionLookup = new HashTriMap<>(null);
    
    static {
        ioServices();
        conversionServices();
        loaded = true;
    }
    
    private static void ioServices() {
        ServiceLoader<FileIOService> ioServices = ServiceLoader.load(FileIOService.class);
        for (FileIOService service : ioServices) {
            if (formatIOLookup.containsKey(service.getFileExtension()))
                log.error(
                    "Format Conversion Collision! "+
                    formatIOLookup.get(service.getFileExtension()).getClass().getSimpleName()+
                    " Will be overwritten by "+
                    service.getClass().getSimpleName()+
                    "!");
            formatIOLookup.put(
                    service.getFileExtension(),
                    service
            );
        }
    }
    
    private static void conversionServices() {
        ServiceLoader<FileConversionService> converterServices = ServiceLoader.load(FileConversionService.class);
        for (FileConversionService service : converterServices) {
            if (formatConversionLookup.containsKey(service.consumes(), service.targets()))
                log.error(
                        "Format Conversion Collision! "+
                        formatConversionLookup.get(service.consumes(), service.targets()).getClass().getSimpleName()+
                        " Will be overwritten by "+
                        service.getClass().getSimpleName()+
                        "!");
            formatConversionLookup.put(
                    service.consumes(),
                    service.targets(),
                    service
            );
        }
    }
    
    public static FileIOService getIOService(String extension) {
        return formatIOLookup.get(extension);
    }
    
    public static FileConversionService getConversionService(String consumes, String targets) {
        return formatConversionLookup.get(consumes, targets);
    }
}
