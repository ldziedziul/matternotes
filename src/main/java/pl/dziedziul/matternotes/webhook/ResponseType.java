package pl.dziedziul.matternotes.webhook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseType {
	IN_CHANNEL, EPHEMERAL;

	@JsonCreator
	public static ResponseType fromString(String key) {
		return key == null ? null : ResponseType.valueOf(key.toUpperCase());
	}

	@JsonValue
	public String toJson() {
		return name().toLowerCase();
	}
}
