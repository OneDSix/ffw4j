package net.onedsix.ffw4j.services.container.composition;

import java.util.Date;

public interface ModifiableContainer {
	void setBool(String query, boolean data) throws Exception;
	void setByte(String query, byte data) throws Exception;
	void setDate(String query, Date data) throws Exception;
	void setDouble(String query, double data) throws Exception;
	void setFloat(String query, float data) throws Exception;
	void setInt(String query, int data) throws Exception;
	void setLong(String query, long data) throws Exception;
	void setNumber(String query, Number data) throws Exception;
	void setShort(String query, short data) throws Exception;
	void setString(String query, String data) throws Exception;
}
