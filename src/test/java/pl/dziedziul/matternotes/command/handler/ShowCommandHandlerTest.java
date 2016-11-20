package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteType;
import pl.dziedziul.matternotes.domain.TestDataBuilder;
import pl.dziedziul.matternotes.domain.service.NoteService;
import pl.dziedziul.matternotes.webhook.NoteRenderer;
import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class ShowCommandHandlerTest {
	private MessageArgumentParser messageArgumentParser = mock(MessageArgumentParser.class);
	private NoteService noteService = mock(NoteService.class);
	private NoteRenderer noteRenderer = mock(NoteRenderer.class);
	private ShowCommandHandler sut = new ShowCommandHandler(noteService, messageArgumentParser, noteRenderer);
	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Test
	public void shouldSupportShowAction() throws Exception {
		//given
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.SHOW);
		boolean supporting = sut.isSupporting(command);
		//then
		assertTrue(supporting);
	}

	@Test
	public void shouldNotSupportOtherAction() throws Exception {
		//given
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.FEEDBACK);
		boolean supporting = sut.isSupporting(command);
		//then
		assertFalse(supporting);
	}

	@Test
	public void shouldHandleShowSingleTitledNote() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("title", ""));
		Note persistentNote = testDataBuilder.createValidNoteWith2Message(NoteType.TITLED);
		when(noteService.getNoteByExample(any())).thenReturn(Optional.of(persistentNote));
		when(noteRenderer.render(persistentNote)).thenReturn("some rendered text");
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is("some rendered text"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

	@Test
	public void shouldHandleShowAllNotes() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("title", ShowCommandHandler.ALL));
		Note persistentNote1 = testDataBuilder.createValidNoteWith2Message(NoteType.TITLED);
		Note persistentNote2 = testDataBuilder.createValidNoteWith2Message(NoteType.CHANNEL);
		when(noteService.getAllNotes(any())).thenReturn(Arrays.asList(persistentNote1, persistentNote2));
		when(noteRenderer.render(anyListOf(Note.class))).thenReturn("some multi note rendered text");
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is("some multi note rendered text"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

	@Test
	public void shouldHandleShowAllNotesWheNoNotes() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("title", ShowCommandHandler.ALL));
//		when(noteService.getAllNotes(any())).thenReturn(Arrays.asList(persistentNote1, persistentNote2));
		when(noteRenderer.renderNoNotes()).thenReturn("no notes rendered text");
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is("no notes rendered text"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

}