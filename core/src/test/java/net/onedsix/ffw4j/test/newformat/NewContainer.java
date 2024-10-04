package net.onedsix.ffw4j.test.newformat;

import net.onedsix.ffw4j.services.container.AbstractBaseContainer;
import net.onedsix.ffw4j.services.container.AbstractObjectiveContainer;

public class NewContainer extends AbstractObjectiveContainer {
    @Override
    public String getFileExtension() {
        return null;
    }
    
    @Override
    public void close() throws Exception {
    
    }
    
    @Override
    public void setVal(String query, Object data) {
    
    }
    
    @Override
    public Object getVal(String query) throws Exception {
        return null;
    }
}
