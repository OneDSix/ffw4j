package net.onedsix.ffw4j.legacy.converters;

import com.google.gson.JsonObject;

public interface JsonFileConverter<T> extends ConvertibleFile<T> {
	JsonObject convertToJson(T data) throws Exception;
}
