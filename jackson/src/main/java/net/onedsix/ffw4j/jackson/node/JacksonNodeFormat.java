package net.onedsix.ffw4j.jackson.node;

import com.fasterxml.jackson.databind.JsonNode;
import net.onedsix.ffw4j.services.container.AbstractContainer;
import net.onedsix.ffw4j.services.container.composition.QueryableContainer;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class JacksonNodeFormat extends AbstractContainer implements QueryableContainer {
    
    public final JsonNode node;
    
    public JacksonNodeFormat(JsonNode node) {
        this.node = node;
    }
    
    @Override
    public String getFileExtension() {
        return null;
    }
    
    @Override
    public boolean getBool(String query) {
        return node.get(query).asBoolean();
    }
    
    @Override
    public byte getByte(String query) {
        return (byte) node.get(query).asInt();
    }
    
    @Override
    public Date getDate(String query) throws ParseException {
        return DateFormat.getInstance().parse(node.get(query).asText());
    }
    
    @Override
    public double getDouble(String query) {
        return node.get(query).asDouble();
    }
    
    @Override
    public float getFloat(String query) {
        return (float) node.get(query).asLong();
    }
    
    @Override
    public int getInt(String query) {
        return node.get(query).asInt();
    }
    
    @Override
    public long getLong(String query) {
        return node.get(query).asLong();
    }
    
    @Override
    public Number getNumber(String query) {
        return getLong(query);
    }
    
    @Override
    public short getShort(String query) {
        return (short) getLong(query);
    }
    
    @Override
    public String getString(String query) {
        return node.get(query).asText();
    }
    
    @Override
    public void close() {
    
    }
}
