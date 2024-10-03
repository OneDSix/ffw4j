package net.onedsix.ffw4j.legacy;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.onedsix.ffw4j.services.container.composition.CastableFile;
import net.onedsix.ffw4j.services.container.composition.PrimitiveModifiableFile;
import net.onedsix.ffw4j.legacy.converters.JsonFileConverter;
import net.onedsix.ffw4j.legacy.composition.*;

import java.io.File;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

public class PropertiesFile<C> extends AbstractFileFormat<Properties> implements
        PrimitiveModifiableFile, CastableFile<Properties, C>,
            JsonFileConverter<Properties> {
	@Override
	public C cast(Properties data) throws Exception {
		return null;
	}

	@Override
	public C castRead(File file, Class<C> cast) throws Exception {
		GsonFile<C> json = new GsonFile<>();
		Properties prop = this.read(file);
		String converted = this.convertToJson(prop).toString();
		return json.gson.fromJson(converted, cast);
	}

	@Override
	public boolean castWrite(File file, C cast) throws Exception {
		GsonFile<C> json = new GsonFile<>();
		String stringified = json.gson.toJson(cast);
		Properties reverted = json.convertToProperties(JsonParser.parseString(stringified).getAsJsonObject());
		this.write(file, reverted);
		return false;
	}

	@Override
	public Properties read(File file) throws Exception {
		Properties prop = new Properties();
		prop.load(Files.newInputStream(file.toPath()));
		return prop;
	}

	@Override
	public boolean write(File file, Properties data) throws Exception {
		data.store(Files.newOutputStream(file.toPath()), null);
		return false;
	}

	@Override
	public JsonObject convertToJson(Properties data) {
		JsonObject jsonObject = new JsonObject();
		for (String key : data.stringPropertyNames()) {
			jsonObject.addProperty(key, data.getProperty(key));
		}
		return jsonObject;
	}

	@Override
	public boolean getBool(String query) {
		return Boolean.parseBoolean(this.value.getProperty(query));
	}

	@Override
	public byte getByte(String query) {
		return Byte.parseByte(this.value.getProperty(query));
	}

	@Override
	public Date getDate(String query) throws ParseException {
		return DateFormat.getDateInstance().parse(this.value.getProperty(query));
	}

	@Override
	public double getDouble(String query) {
		return Double.parseDouble(this.value.getProperty(query));
	}

	@Override
	public float getFloat(String query) {
		return Float.parseFloat(this.value.getProperty(query));
	}

	@Override
	public int getInt(String query) {
		return Integer.parseInt(this.value.getProperty(query));
	}

	@Override
	public long getLong(String query) {
		return Long.getLong(this.value.getProperty(query));
	}

	@Override
	public Number getNumber(String query) {
		return this.getInt(query);
	}

	@Override
	public short getShort(String query) {
		return Short.parseShort(this.value.getProperty(query));
	}

	@Override
	public String getString(String query) {
		return this.value.getProperty(query);
	}

	@Override
	public void setBool(String query, boolean data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setByte(String query, byte data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setDate(String query, Date data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setDouble(String query, double data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setFloat(String query, float data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setInt(String query, int data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setLong(String query, long data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setNumber(String query, Number data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setShort(String query, short data) {
		this.value.setProperty(query, String.valueOf(data));
	}

	@Override
	public void setString(String query, String data) {
		this.value.setProperty(query, data);
	}

	@Override
	public Properties getValue() {
		return this.value;
	}
}
