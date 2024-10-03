package net.onedsix.ffw4j.legacy;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.moandjiezana.toml.Toml;
import lombok.extern.slf4j.Slf4j;
import net.onedsix.storage.files.composition.*;
import net.onedsix.storage.files.converters.*;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class GsonFile<C> extends AbstractFileFormat<JsonObject> implements
	ModifyableFile, CastableFile<JsonObject, C>,
		PropertiesFileConverter<JsonObject>, TomlFileConverter<JsonObject>,
			XmlFileConverter<JsonObject> {
	/** An internal helper variable for converting between file types, and weird GSON operations. */
	public static final Type GsonRelfectionType = new TypeToken<Map<String, Object>>() {}.getType();
	/** This instance's Gson */
	public final Gson gson;

	public GsonFile() {
		super();
		this.gson = new Gson();
	}

	public GsonFile(Gson gson) {
		super();
		this.gson = gson;
	}

	public GsonFile(File file) throws Exception {
		super(file);
		this.gson = new Gson();
	}

	public GsonFile(File file, Gson gson) throws Exception {
		super(file);
		this.gson = gson;
	}

	@Override
	public C cast(JsonObject data) throws Exception {
		return null;
	}

	/**
	 * @param file The .JSON file to read
	 * @param cast The class type to which JSON should be deserialized
	 * @return Returns an instance of the specified class populated with JSON data
	 */
	@Override
	public C castRead(File file, Class<C> cast) throws IOException {
		try(Reader reader = new FileReader(file)) {
			return this.gson.fromJson(reader, cast);
		}
	}

	@Override
	public boolean castWrite(File file, C cast) throws IOException {
		try (Writer writer = new FileWriter(file)) {
			this.gson.toJson(cast, writer);
		}
		return false;
	}
	/**
	 * @param file The .JSON file to read
	 * @return Returns a {@link JsonObject}
	 * */
	@Override
	public JsonObject read(File file) throws IOException {
		try (Reader reader = new FileReader(file)) {
			return JsonParser.parseReader(reader).getAsJsonObject();
		}
	}

	/**
	 * @param json A {@link JsonObject}
	 * @param file The .JSON file to read
	 * @return <code>true</code> if the write changed any data
	 */
	@Override
	public boolean write(File file, JsonObject json) throws IOException {
		try (Writer writer = new FileWriter(file)) {
			this.gson.toJson(json, writer);
		}
		return false;
	}

	@Override
	public Properties convertToProperties(JsonObject data) throws Exception {
		return null;
	}

	@Override
	public Toml convertToToml(JsonObject data) {
		StringBuilder sb = new StringBuilder();
		mapToToml(data.asMap(), sb, "");
		return new Toml().read(sb.toString());
	}

	/** Method for manually converting a {@code Map<String, JsonElement>} to a Toml-Compatible string using a heavy amount of string manipulation. */
	@SuppressWarnings("unchecked")
	protected static void mapToToml(Map<String, JsonElement> map, StringBuilder toml, String prefix) {
		for (Map.Entry<String, JsonElement> entry : map.entrySet()) {
			if (entry.getValue() instanceof Map) {
				toml.append("[").append(prefix).append(entry.getKey()).append("]\n");
				mapToToml((Map<String, JsonElement>) entry.getValue(), toml, prefix + entry.getKey() + ".");
			} else if (entry.getValue() instanceof Iterable) {
				toml.append(prefix)
					.append(entry.getKey())
					.append(" = [");
				for (Object obj : (Iterable<?>) entry.getValue()) {
					toml.append(obj.toString())
						.append(", ");
				}
				toml.delete(toml.length() - 2, toml.length())
					.append("]\n");
			} else {
				toml.append(prefix)
					.append(entry.getKey())
					.append(" = ")
					.append(entry.getValue().toString())
					.append("\n");
			}
		}
	}

	@Override
	public JSONObject convertToXml(JsonObject data) {
		return new JSONObject(data.toString());
	}

	@Override
	public boolean getBool(String query) {
		return this.value.getAsJsonPrimitive(query).getAsBoolean();
	}

	public BigDecimal getBigDecimal(String query) {
		return this.value.getAsJsonPrimitive(query).getAsBigDecimal();
	}

	public BigInteger getBigInteger(String query) {
		return this.value.getAsJsonPrimitive(query).getAsBigInteger();
	}

	@Override
	public byte getByte(String query) {
		return this.value.getAsJsonPrimitive(query).getAsByte();
	}

	/** @deprecated This method uses {@link JsonPrimitive#getAsCharacter()}, which is deprecated due to being misleading.
	 * This method returns the first character of the string, if a string is present. If the string is a single character,
	 * then this method works as intended.
	 * @see JsonPrimitive#getAsCharacter() */
	@Deprecated
	public Character getCharacter(String query) {
		return this.value.getAsJsonPrimitive(query).getAsCharacter();
	}

	@Override
	public Date getDate(String query) throws ParseException {
		return DateFormat.getDateInstance().parse(this.value.getAsJsonPrimitive(query).getAsString());
	}

	@Override
	public double getDouble(String query) {
		return this.value.getAsJsonPrimitive(query).getAsDouble();
	}

	@Override
	public float getFloat(String query) {
		return this.value.getAsJsonPrimitive(query).getAsFloat();
	}

	@Override
	public int getInt(String query) {
		return this.value.getAsJsonPrimitive(query).getAsInt();
	}

	@Override
	public long getLong(String query) {
		return this.value.getAsJsonPrimitive(query).getAsLong();
	}

	@Override
	public Number getNumber(String query) {
		return this.value.getAsJsonPrimitive(query).getAsNumber();
	}

	@Override
	public short getShort(String query) {
		return this.value.getAsJsonPrimitive(query).getAsShort();
	}

	@Override
	public String getString(String query) {
		return this.value.getAsJsonPrimitive(query).getAsString();
	}

	@Override
	public void setBool(String query, boolean data) {
		this.value.addProperty(query, data);
	}

	public void setBigDecimal(String query, BigDecimal data) {
		this.value.addProperty(query, data);
	}

	public void setBigInteger(String query, BigInteger data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setByte(String query, byte data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setDate(String query, Date data) {
		this.value.addProperty(query, data.toString());
	}

	@Override
	public void setDouble(String query, double data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setFloat(String query, float data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setInt(String query, int data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setLong(String query, long data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setNumber(String query, Number data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setShort(String query, short data) {
		this.value.addProperty(query, data);
	}

	@Override
	public void setString(String query, String data) {
		this.value.addProperty(query, data);
	}

	@Override
	public JsonObject getValue() {
		return this.value;
	}
}
