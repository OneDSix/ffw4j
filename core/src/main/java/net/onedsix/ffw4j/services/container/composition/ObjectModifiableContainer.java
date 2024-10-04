package net.onedsix.ffw4j.services.container.composition;

import java.util.Date;

public interface ObjectModifiableContainer extends ModifiableContainer {
    @Override
    default void setBool(String query, boolean data) {
        setVal(query, data);
    }
    
    @Override
    default void setByte(String query, byte data) {
        setVal(query, data);
    }
    
    @Override
    default void setDate(String query, Date data) {
        setVal(query, data);
    }
    
    @Override
    default void setDouble(String query, double data) {
        setVal(query, data);
    }
    
    @Override
    default void setFloat(String query, float data) {
        setVal(query, data);
    }
    
    @Override
    default void setInt(String query, int data) {
        setVal(query, data);
    }
    
    @Override
    default void setLong(String query, long data) {
        setVal(query, data);
    }
    
    @Override
    default void setNumber(String query, Number data) {
        setVal(query, data);
    }
    
    @Override
    default void setShort(String query, short data) {
        setVal(query, data);
    }
    
    @Override
    default void setString(String query, String data) {
        setVal(query, data);
    }
    
    void setVal(String query, Object data);
}
