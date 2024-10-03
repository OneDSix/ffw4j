package net.onedsix.ffw4j.legacy;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import lombok.extern.slf4j.Slf4j;
import net.onedsix.ffw4j.services.container.composition.CastableFile;
import net.onedsix.ffw4j.services.container.composition.PrimitiveQueryableFile;
import net.onedsix.ffw4j.legacy.converters.JsonFileConverter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
public class TomlFile<C> extends AbstractFileFormat<Toml> implements
        PrimitiveQueryableFile, CastableFile<Toml, C>, JsonFileConverter<Toml> {
	private final TomlWriter writer;

	public TomlFile(TomlWriter writer) {
		this.writer = writer;
	}

	public TomlFile() {
		this.writer = new TomlWriter();
	}

	@Override
	public C cast(Toml data) throws Exception {
		return null;
	}

	@Override
	public C castRead(File file, Class<C> cast) {
		return new Toml().read(file).to(cast);
	}

	@Override
	public boolean castWrite(File file, C cast) throws IOException {
		this.writer.write(cast, file);
		return false;
	}

	@Override
	public Toml read(File file) {
		return new Toml().read(file);
	}

	@Override
	public boolean write(File file, Toml toml) throws IOException {
		this.writer.write(toml, file);
		return false;
	}

	@Override
	public JsonObject convertToJson(Toml data) throws Exception {
		Map<String, Object> tomlData = data.toMap();
		String jsonString = new GsonFile<C>().gson.toJson(tomlData);
		return JsonParser.parseString(jsonString).getAsJsonObject();
	}

	@Override
	public boolean getBool(String query) {
		return this.value.getBoolean(query);
	}

	@Override
	public byte getByte(String query) {
		return this.value.getLong(query).byteValue();
	}

	@Override
	public Date getDate(String query) {
		return this.value.getDate(query);
	}

	@Override
	public double getDouble(String query) {
		return this.value.getDouble(query);
	}

	@Override
	public float getFloat(String query) {
		return this.value.getDouble(query).floatValue();
	}

	@Override
	public int getInt(String query) {
		return this.value.getDouble(query).intValue();
	}

	@Override
	public long getLong(String query) {
		return this.value.getLong(query);
	}

	@Override
	public Number getNumber(String query) {
		return this.value.getLong(query);
	}

	@Override
	public short getShort(String query) {
		return this.value.getLong(query).shortValue();
	}

	@Override
	public String getString(String query) {
		return this.value.getString(query);
	}

	@Override
	public Toml getValue() {
		return this.value;
	}
}
