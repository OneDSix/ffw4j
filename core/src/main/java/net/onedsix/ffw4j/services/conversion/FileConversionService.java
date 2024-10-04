package net.onedsix.ffw4j.services.conversion;

import net.onedsix.ffw4j.services.container.AbstractContainer;

/**
 * @param <T> The target type
 * @param <C> The consumed object
 * */
public interface FileConversionService<T extends AbstractContainer, C extends AbstractContainer> {
    //
    T convert(C consumes);
    
    //
    String targets();
    //
    String consumes();
}
