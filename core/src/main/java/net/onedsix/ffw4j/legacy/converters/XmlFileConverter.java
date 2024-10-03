package net.onedsix.ffw4j.legacy.converters;

import org.json.JSONObject;

public interface XmlFileConverter<T> extends ConvertibleFile<T> {
	JSONObject convertToXml(T data) throws Exception;
}
