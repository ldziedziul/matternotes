package pl.dziedziul.matternotes.model;

import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public final class TestDataBuilder {

	private TestDataBuilder() {
	}

	private static final String VALID_SLASH_COMMAND_RESULT_AS_JSON = "{\n" +
		"  \"text\" : \"some text\",\n" +
		"  \"response_type\" : \"in_channel\",\n" +
		"  \"goto_location\" : \"http://example.com\"\n" +
		"}";

	public String validSlashCommandResultAsJson() {
		return VALID_SLASH_COMMAND_RESULT_AS_JSON;
	}

	public SlashCommandResult validSlashCommandResult() {
		return SlashCommandResult.Builder.newSlashCommandResult()
			.gotoLocation("http://example.com")
			.responseType(ResponseType.IN_CHANNEL)
			.text("some text")
			.build();
	}

	public static TestDataBuilder getInstance() {
		return new TestDataBuilder();
	}
}
