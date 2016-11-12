package pl.dziedziul.matternotes.mapper;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.*;

import java.io.IOException;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {

	private ObjectMapper mapper;

	public JsonMapper() {
		mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, ANY);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public <T> T fromJson(String json, Class<T> clazz) {
		try {
			return mapper.reader()
				.forType(clazz)
				.readValue(json);
		} catch (IOException e) {
			throw new RuntimeException("Problem during deserialization from json", e);
		}
	}

	public String toJson(Object obj) {
		if (obj == null) {
			return null;
		}

		try {
			return mapper.writer().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Problem during serialization to json", e);
		}
	}
}
