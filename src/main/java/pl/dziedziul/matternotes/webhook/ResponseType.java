package pl.dziedziul.matternotes.webhook;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseType {
	IN_CHANNEL, EPHEMERAL;

	@JsonCreator
	static ResponseType fromString(String key) {
		return StringUtils.isBlank(key) ? null : ResponseType.valueOf(key.toUpperCase());
	}

	@JsonValue
	String toJson() {
		return name().toLowerCase();
	}
}
