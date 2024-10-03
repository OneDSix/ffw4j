package net.onedsix.ffw4j.legacy;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import net.onedsix.ffw4j.services.container.composition.CastableFile;
import net.onedsix.ffw4j.services.container.composition.PrimitiveModifiableFile;
import net.onedsix.ffw4j.legacy.converters.XmlFileConverter;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;

public class PlistFile<C> extends AbstractFileFormat<NSDictionary> implements
        PrimitiveModifiableFile, CastableFile<NSDictionary, C>,
                XmlFileConverter<NSDictionary> {
	@Override
	public C cast(NSDictionary data) throws Exception {
		return null;
	}

	@Override
	public C castRead(File file, Class<C> cast) throws Exception {
		return null;
	}

	@Override
	public boolean castWrite(File file, C cast) throws Exception {
		return false;
	}

	@Override
	public NSDictionary read(File file) throws Exception {
		return (NSDictionary) PropertyListParser.parse(file);
	}

	@Override
	public boolean write(File file, NSDictionary data) throws Exception {
		return false;
	}

	@Override
	public JSONObject convertToXml(NSDictionary data) throws Exception {
		return null;
	}

	@Override
	public void setBool(String query, boolean data) throws Exception {

	}

	@Override
	public void setByte(String query, byte data) throws Exception {

	}

	@Override
	public void setDate(String query, Date data) throws Exception {

	}

	@Override
	public void setDouble(String query, double data) throws Exception {

	}

	@Override
	public void setFloat(String query, float data) throws Exception {

	}

	@Override
	public void setInt(String query, int data) throws Exception {

	}

	@Override
	public void setLong(String query, long data) throws Exception {

	}

	@Override
	public void setNumber(String query, Number data) throws Exception {

	}

	@Override
	public void setShort(String query, short data) throws Exception {

	}

	@Override
	public void setString(String query, String data) throws Exception {

	}

	@Override
	public boolean getBool(String query) throws Exception {
		return false;
	}

	@Override
	public byte getByte(String query) throws Exception {
		return 0;
	}

	@Override
	public Date getDate(String query) throws Exception {
		return null;
	}

	@Override
	public double getDouble(String query) throws Exception {
		return 0;
	}

	@Override
	public float getFloat(String query) throws Exception {
		return 0;
	}

	@Override
	public int getInt(String query) throws Exception {
		return 0;
	}

	@Override
	public long getLong(String query) throws Exception {
		return 0;
	}

	@Override
	public Number getNumber(String query) throws Exception {
		return null;
	}

	@Override
	public short getShort(String query) throws Exception {
		return 0;
	}

	@Override
	public String getString(String query) throws Exception {
		return null;
	}

	@Override
	public NSDictionary getValue() {
		return this.value;
	}
}
