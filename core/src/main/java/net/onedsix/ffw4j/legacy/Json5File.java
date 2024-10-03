package net.onedsix.ffw4j.legacy;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class Json5File {

	static {
		JsonMapper.builder().enable(
			JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES,
			JsonReadFeature.ALLOW_TRAILING_COMMA,
			JsonReadFeature.ALLOW_SINGLE_QUOTES,
			JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER,
			JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS,
			JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS,
			JsonReadFeature.ALLOW_JAVA_COMMENTS,
			JsonReadFeature.ALLOW_YAML_COMMENTS,
			JsonReadFeature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS
		).build();

		new JsonFactory()
			.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
			.enable(JsonParser.Feature.ALLOW_TRAILING_COMMA)
			.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
			.enable(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
			.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)
			.enable(JsonParser.Feature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS)
			.enable(JsonParser.Feature.ALLOW_COMMENTS)
			.enable(JsonParser.Feature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS);
	}
}
