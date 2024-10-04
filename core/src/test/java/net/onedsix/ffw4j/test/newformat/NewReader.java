package net.onedsix.ffw4j.test.newformat;

import net.onedsix.ffw4j.services.reader.FileIOService;

import java.io.File;

public class NewReader implements FileIOService<NewContainer> {
    @Override
    public String getFileExtension() {
        return null;
    }
    
    @Override
    public NewContainer read(File file) throws Exception {
        return null;
    }
    
    @Override
    public boolean write(File file, NewContainer data) throws Exception {
        return false;
    }
}
