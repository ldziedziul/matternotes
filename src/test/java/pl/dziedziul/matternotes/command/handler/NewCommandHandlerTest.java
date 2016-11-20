package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.service.NoteService;
import pl.dziedziul.matternotes.domain.service.UpsertNoteResult;
import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class NewCommandHandlerTest {
	private MessageArgumentParser messageArgumentParser = mock(MessageArgumentParser.class);
	private NoteService noteService = mock(NoteService.class);
	private NewCommandHandler sut = new NewCommandHandler(noteService, messageArgumentParser);

	@Test
	public void shouldSupportNewAction() throws Exception {
		//given
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.NEW);
		boolean supporting = sut.isSupporting(command);
		//then
		assertTrue(supporting);
	}

	@Test
	public void shouldNotSupportOtherAction() throws Exception {
		//given
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.ABOUT);
		boolean supporting = sut.isSupporting(command);
		//then
		assertFalse(supporting);
	}

	@Test
	public void shouldHandleFirstMessageWithTitle() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("title", "some text"));
		when(noteService.upsertNote(any())).thenReturn(UpsertNoteResult.INSERTED);
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is(":pencil: Note created. Run `/notes show [title]` to read it"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}


	@Test
	public void shouldHandleFirstMessageWithoutTitle() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("", "some text"));
		when(noteService.upsertNote(any())).thenReturn(UpsertNoteResult.INSERTED);
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is(":pencil: Note created. Run `/notes show` to read it"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}


	@Test
	public void shouldHandleNonFirstMessageWithTitle() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("title", "some text"));
		when(noteService.upsertNote(any())).thenReturn(UpsertNoteResult.UPDATED);
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is(":pencil: Note updated. Run `/notes show [title]` to read it"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}


	@Test
	public void shouldHandleNonFirstMessageWithoutTitle() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("", "some text"));
		when(noteService.upsertNote(any())).thenReturn(UpsertNoteResult.UPDATED);
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is(":pencil: Note updated. Run `/notes show` to read it"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

}