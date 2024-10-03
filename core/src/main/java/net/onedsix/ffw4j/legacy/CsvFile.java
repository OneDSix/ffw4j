package net.onedsix.ffw4j.legacy;

import com.google.gson.JsonObject;
import com.moandjiezana.toml.Toml;
import net.onedsix.ffw4j.services.container.composition.CastableFile;
import net.onedsix.ffw4j.services.container.composition.PrimitiveModifiableFile;
import net.onedsix.ffw4j.legacy.converters.JsonFileConverter;
import net.onedsix.ffw4j.legacy.converters.PropertiesFileConverter;
import net.onedsix.ffw4j.legacy.converters.TomlFileConverter;
import net.onedsix.ffw4j.legacy.converters.XmlFileConverter;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/** This specific CSV implementation adds no spaces next to the comma, and if there is one its added to the string value.
 * This may be changed at a later date. */
public class CsvFile<C> extends AbstractFileFormat<CsvFile.CsvContent> implements
        PrimitiveModifiableFile, CastableFile<CsvFile.CsvContent, C>,
		JsonFileConverter<CsvFile.CsvContent>, PropertiesFileConverter<CsvFile.CsvContent>,
			TomlFileConverter<CsvFile.CsvContent>, XmlFileConverter<CsvFile.CsvContent> {

	@Override
	public boolean write(File file, CsvContent data) throws Exception {
		return writePlain(file, data.getText());
	}

	@Override
	public CsvFile.CsvContent read(File file) throws IOException {
		this.value = new CsvContent(file);
		return this.value;
	}

	@Override
	public C cast(CsvContent data) throws Exception {
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
	public JsonObject convertToJson(CsvContent data) throws Exception {
		return null;
	}

	@Override
	public Properties convertToProperties(CsvContent data) throws Exception {
		return null;
	}

	@Override
	public Toml convertToToml(CsvContent data) throws Exception {
		return null;
	}

	@Override
	public JSONObject convertToXml(CsvContent data) throws Exception {
		return null;
	}

	@Override
	public CsvContent getValue() {
		return this.value;
	}

	public static class CsvContent {
		public Map<String, String[]> lines;

		public String[] get(String query) {
			return lines.get(query);
		}

		public String getText() {
			StringBuilder sb = new StringBuilder();
			lines.forEach((key, strings) ->
			  sb.append(key)
				.append(convertToSingleLine(strings))
				.append("\n"));
			return sb.toString();
		}

		private String convertToSingleLine(String[] strings) {
			StringBuilder sb = new StringBuilder();
			for (String str : strings) {
				sb.append(",")
				  .append(str);
			}
			return sb.toString();
		}

		public CsvContent(File file) throws IOException {
			Map<String, String[]> parsedLines = new LinkedHashMap<>();

			try (InputStream is = Files.newInputStream(file.toPath())) {
				List<Byte> previousBytes = new LinkedList<>();
				boolean continueLooping = true;

				while (continueLooping) {
					int nextByte = is.read();

					// This might not take into account the \n\r return type, or if it does it might mess up the next line.
					// Needs more testing.
					if (nextByte == '\n' || nextByte == '\r') {
						// This statement here might fix the above issue. Again, needs more testing.
						if (!previousBytes.isEmpty()) {
							byte[] byteArray = new byte[previousBytes.size()];
							for (int i = 0; i < previousBytes.size(); i++) {
								byteArray[i] = previousBytes.get(i);
							}

							String line = new String(byteArray, StandardCharsets.UTF_8);
							String[] split = line.split(",");
							String key = split[0];
							String[] values = Arrays.copyOfRange(split, 1, split.length);
							parsedLines.put(key, values);
							previousBytes.clear();
						}
					} else if (nextByte == -1) {
						continueLooping = false;

						if (!previousBytes.isEmpty()) {
							byte[] byteArray = new byte[previousBytes.size()];
							for (int i = 0; i < previousBytes.size(); i++) {
								byteArray[i] = previousBytes.get(i);
							}

							String line = new String(byteArray, StandardCharsets.UTF_8);
							String[] split = line.split(" ");
							String key = split[0];
							String[] values = Arrays.copyOfRange(split, 1, split.length);
							parsedLines.put(key, values);
						}
					} else {
						previousBytes.add((byte) nextByte);
					}

				}
			}
			this.lines = parsedLines;
		}
	}
}
