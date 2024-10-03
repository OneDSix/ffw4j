package net.onedsix.ffw4j.legacy.converters;

import java.util.Properties;

public interface PropertiesFileConverter<T> extends ConvertibleFile<T> {
	Properties convertToProperties(T data) throws Exception;
}
