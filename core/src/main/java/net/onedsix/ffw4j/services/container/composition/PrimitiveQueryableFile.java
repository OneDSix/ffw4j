package net.onedsix.ffw4j.services.container.composition;

import java.util.Date;

public interface PrimitiveQueryableFile {
	boolean getBool(String query) throws Exception;
	byte getByte(String query) throws Exception;
	Date getDate(String query) throws Exception;
	double getDouble(String query) throws Exception;
	float getFloat(String query) throws Exception;
	int getInt(String query) throws Exception;
	long getLong(String query) throws Exception;
	Number getNumber(String query) throws Exception;
	short getShort(String query) throws Exception;
	String getString(String query) throws Exception;
}
