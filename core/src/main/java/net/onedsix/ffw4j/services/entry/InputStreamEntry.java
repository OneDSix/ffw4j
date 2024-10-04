package net.onedsix.ffw4j.services.entry;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.function.Function;

public class InputStreamEntry extends AbstractFileEntry<InputStream> {
    public InputStreamEntry(InputStream format) {
        super(format);
    }
    public InputStreamEntry() {
        super();
    }
    
    @Override
    public Class<InputStream> targetClass() {
        return InputStream.class;
    }
    
    @Override
    public Function<InputStream, AbstractFileEntry<InputStream>> lambdaGet() {
        return InputStreamEntry::new;
    }
    
    @Override
    public File toFile() {
        try {
            // This should not be deleted!!
            File notTempFile = Files.createTempFile("ffw4j_tempfile_inputstream", null).toFile();
            FileUtils.copyInputStreamToFile(this.format, notTempFile);
            return notTempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean isDirectory() {
        return toFile().isDirectory();
    }
}
