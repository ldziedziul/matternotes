package pl.dziedziul.matternotes.domain;

import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
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
		return SlashCommandResult.Builder.newSlashCommandResult("some text")
			.gotoLocation("http://example.com")
			.responseType(ResponseType.IN_CHANNEL)
			.build();
	}

	private SlashCommand validSlashCommand(String firstWord) {
		return SlashCommand.Builder.newSlashCommand()
			.channelId("some-channel-id")
			.channelName("some channel name")
			.command("/some-command")
			.responseUrl("some-response-url")
			.teamDomain("some-team-domain")
			.teamId("some-team-id")
			.text(firstWord + " sample text")
			.token("some-token")
			.userId("some-user-id")
			.userName("some user name")
			.build();
	}

	public SlashCommand validSlashCommand() {
		return validSlashCommand("Some");
	}

	public static TestDataBuilder getInstance() {
		return new TestDataBuilder();
	}

	public Note createValidNoteWithSingleMessage(String name, String userId, NoteType type) {
		Note note = new Note();
		note.setType(type);
		note.setUsername("some username");
		note.setUserId(userId);
		note.setTitle("title-" + name);
		note.addMessage("message-" + name);
		note.setChannelId("channelId-" + name);
		note.setChannelName("channelName-" + name);
		return note;
	}

	public Note createValidNoteWithSingleMessage(NoteType type) {
		return createValidNoteWithSingleMessage("1", "some-user-id", type);
	}

	public Note createValidNoteWith2Messages(String name, String userId, NoteType type) {
		Note note = createValidNoteWithSingleMessage(name, userId, type);
		note.addMessage("second message");
		return note;
	}

	public Note createValidNoteWith2Message(NoteType type) {
		return createValidNoteWith2Messages("1", "some-user-id", type);
	}
}
