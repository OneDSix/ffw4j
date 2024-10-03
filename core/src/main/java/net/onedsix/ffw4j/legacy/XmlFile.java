package net.onedsix.ffw4j.legacy;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.onedsix.ffw4j.services.container.composition.CastableFile;
import net.onedsix.ffw4j.services.container.composition.PrimitiveModifiableFile;
import net.onedsix.ffw4j.legacy.converters.JsonFileConverter;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/** Utilities for XML files. Know that internally all XML data is actually a {@link org.json.JSONObject} instance,
 * (<i>not</i> a {@link JsonObject}!) can hence can be converted to JSON or GSON in a one-liner. */
public class XmlFile<C> extends AbstractFileFormat<JSONObject> implements
        PrimitiveModifiableFile, CastableFile<JSONObject, C>,
                JsonFileConverter<JSONObject> {
	@Override
	public C cast(JSONObject data) throws Exception {
		return null;
	}

	@Override
	public C castRead(File file, Class<C> cast) throws Exception {
		try(Reader reader = new FileReader(file)) {
			return new GsonFile<C>().gson.fromJson(reader, cast);
		}
	}

	@Override
	public boolean castWrite(File file, C cast) throws Exception {
		GsonFile<C> json = new GsonFile<>();
		String stringified = json.gson.toJson(cast);
		JSONObject reverted = json.convertToXml(JsonParser.parseString(stringified).getAsJsonObject());
		this.write(file, reverted);
		return false;
	}

	@Override
	public JSONObject read(File file) throws Exception {
		return XML.toJSONObject(Files.newBufferedReader(file.toPath()));
	}

	@Override
	public boolean write(File file, JSONObject data) throws Exception {
		Files.write(file.toPath(), xmlFromJson(data).getBytes(StandardCharsets.UTF_8));
		return false;
	}

	@Override
	public JsonObject convertToJson(JSONObject data) throws Exception {
		return JsonParser.parseString(data.toString()).getAsJsonObject();
	}

	public String xmlFromFile(File file) throws Exception {
		return XML.toString(this.read(file));
	}

	public String xmlFromJson(JSONObject json) {
		return XML.toString(json);
	}

	@Override
	public boolean getBool(String query) {
		return this.value.getBoolean(query);
	}

	@Override
	public byte getByte(String query) {
		return this.value.optLongObject(query).byteValue();
	}

	@Override
	public Date getDate(String query) throws ParseException {
		return DateFormat.getDateInstance().parse(this.value.getString(query));
	}

	@Override
	public double getDouble(String query) {
		return this.value.getDouble(query);
	}

	@Override
	public float getFloat(String query) {
		return this.value.getFloat(query);
	}

	@Override
	public int getInt(String query) {
		return this.value.getInt(query);
	}

	@Override
	public long getLong(String query) {
		return this.value.getLong(query);
	}

	@Override
	public Number getNumber(String query) {
		return this.value.getNumber(query);
	}

	@Override
	public short getShort(String query) {
		return Short.parseShort(this.value.getString(query));
	}

	@Override
	public String getString(String query) {
		return this.value.getString(query);
	}


	@Override
	public void setBool(String query, boolean data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setByte(String query, byte data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setDate(String query, Date data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setDouble(String query, double data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setFloat(String query, float data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setInt(String query, int data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setLong(String query, long data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setNumber(String query, Number data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setShort(String query, short data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public void setString(String query, String data) throws Exception {
		this.value.put(query, data);
	}

	@Override
	public JSONObject getValue() {
		return this.value;
	}
}
