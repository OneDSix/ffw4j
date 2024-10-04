package net.onedsix.ffw4j.core;

import lombok.extern.slf4j.Slf4j;
import net.onedsix.ffw4j.services.entry.AbstractFileEntry;
import net.onedsix.ffw4j.services.conversion.FileConversionService;
import net.onedsix.ffw4j.services.reader.FileIOService;
import net.onedsix.ratils.trimap.TriMap;
import net.onedsix.ratils.trimap.hash.HashTriMap;

import java.util.*;
import java.util.function.Function;

/** The entrypoint to FFW4J, loading in all of the needed classes and plugins, allowing for FFW4J to do its job.<br>
 * <br>
 * */
@SuppressWarnings({"rawtypes","unused"})
@Slf4j
public class FFWServiceLoader {
    
    private static boolean debug = false;
    private static boolean loaded = false;
    public static boolean isLoaded() {
        return loaded;
    }
    
    //
    public static final Map<Class<?>, Function<Object, AbstractFileEntry<?>>> converters = new HashMap<>();
    //
    static final Map<String, FileIOService> formatIOLookup = new HashMap<>();
    //
    static final TriMap<String, String, FileConversionService> formatConversionLookup = new HashTriMap<>(null);
    
    public FFWServiceLoader() {
        this(ServiceOptions.ERROR_ON_COLLISION);
    }
    
    public FFWServiceLoader(ServiceOptions... serviceOptions) {
        List<ServiceOptions> options = Arrays.asList(serviceOptions);
        if (options.contains(ServiceOptions.DEBUG_LOGGING)) {
            debug = true;
            log.info("Debug Logging Enabled");
        }
        
        entryServices();
        ioServices();
        conversionServices();
        
        loaded = true;
    }
    
    private void entryServices() {
        ServiceLoader<AbstractFileEntry> entryServices = ServiceLoader.load(AbstractFileEntry.class);
        for (AbstractFileEntry service : entryServices) {
            converters.put(
                    service.targetClass(),
                    service.lambdaGet()
            );
            if (debug) log.info(service.targetClass()+" | "+service.getClass().getSimpleName());
        }
    }
    
    private void ioServices() {
        ServiceLoader<FileIOService> ioServices = ServiceLoader.load(FileIOService.class);
        for (FileIOService service : ioServices) {
            if (formatIOLookup.containsKey(service.getFileExtension())) {
                log.error(
                    "Format Conversion Collision! "+
                    formatIOLookup.get(service.getFileExtension()).getClass().getSimpleName()+
                    " Will be overwritten by "+
                    service.getClass().getSimpleName()+
                    "!");
            }
            formatIOLookup.put(
                    service.getFileExtension(),
                    service
            );
            if (debug) log.info("format ."+service.getFileExtension()+" | "+ service.getClass().getSimpleName());
        }
    }
    
    private void conversionServices() {
        ServiceLoader<FileConversionService> converterServices = ServiceLoader.load(FileConversionService.class);
        for (FileConversionService service : converterServices) {
            if (formatConversionLookup.containsKey(service.consumes(), service.targets())) {
                log.error(
                        "Format Conversion Collision! "+
                        formatConversionLookup.get(service.consumes(), service.targets()).getClass().getSimpleName()+
                        " Will be overwritten by "+
                        service.getClass().getSimpleName()+
                        "!");
            }
            formatConversionLookup.put(
                    service.consumes(),
                    service.targets(),
                    service
            );
            if (debug) log.info(service.consumes()+" > "+service.targets()+" | "+service.getClass().getSimpleName());
        }
    }
    
    //
    public static FileIOService getIOService(String extension) {
        if (!isLoaded()) throw new FFWRuntimeException("FFW4J services have not been loaded yet!");
        return formatIOLookup.get(extension);
    }
    //
    public static FileConversionService getConversionService(String consumes, String targets) {
        if (!isLoaded()) throw new FFWRuntimeException("FFW4J services have not been loaded yet!");
        return formatConversionLookup.get(consumes, targets);
    }
    //
    public static AbstractFileEntry<?> convertToFile(Object obj) throws FFWException {
        if (!isLoaded()) throw new FFWRuntimeException("FFW4J services have not been loaded yet!");
        Function<Object, AbstractFileEntry<?>> converter = converters.get(obj.getClass());
        if (converter != null) {
            return converter.apply(obj);
        } else {
            throw new FFWException(obj.getClass().getSimpleName()+" is not a file-convertible type!");
        }
    }
}
