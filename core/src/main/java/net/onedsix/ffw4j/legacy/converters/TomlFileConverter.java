package net.onedsix.ffw4j.legacy.converters;

import com.moandjiezana.toml.Toml;

public interface TomlFileConverter<T> extends ConvertibleFile<T> {
	Toml convertToToml(T data) throws Exception;
}
