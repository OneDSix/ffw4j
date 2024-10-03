package net.onedsix.ffw4j.services.conversion;

import net.onedsix.ffw4j.services.container.AbstractFormatContainer;

public abstract class FileConversionService<T extends AbstractFormatContainer, C extends AbstractFormatContainer> {
    //
    public abstract T convert(C consumes);
    
    //
    public abstract String targets();
    //
    public abstract String consumes();
}
