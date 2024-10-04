package net.onedsix.ffw4j.services.container.composition;

import java.text.DateFormat;
import java.util.Date;

public interface ObjectQueryableContainer extends QueryableContainer {
    @Override
    default boolean getBool(String query) throws Exception {
        return (boolean) getVal(query);
    }
    
    @Override
    default byte getByte(String query) throws Exception {
        return (byte) getVal(query);
    }
    
    @Override
    default Date getDate(String query) throws Exception {
        return DateFormat.getInstance().parse(getString(query));
    }
    
    @Override
    default double getDouble(String query) throws Exception {
        return (double) getVal(query);
    }
    
    @Override
    default float getFloat(String query) throws Exception {
        return (float) getVal(query);
    }
    
    @Override
    default int getInt(String query) throws Exception {
        return (int) getVal(query);
    }
    
    @Override
    default long getLong(String query) throws Exception {
        return (long) getVal(query);
    }
    
    @Override
    default Number getNumber(String query) throws Exception {
        return getDouble(query);
    }
    
    @Override
    default short getShort(String query) throws Exception {
        return (short) getVal(query);
    }
    
    @Override
    default String getString(String query) throws Exception {
        return (String) getVal(query);
    }
    
    Object getVal(String query) throws Exception;
}
