package net.onedsix.ffw4j.core.entry;

import java.io.File;
import java.util.zip.ZipEntry;

public class ZipEntryEntry extends AbstractFileEntry<ZipEntry> {
    public ZipEntryEntry(ZipEntry format) {
        super(format);
    }
    
    @Override
    public File toFile() {
        return null;
    }
}
