package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.service.NoteService;
import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class UndoCommandHandlerTest {
	private MessageArgumentParser messageArgumentParser = mock(MessageArgumentParser.class);
	private NoteService noteService = mock(NoteService.class);
	private UndoCommandHandler sut = new UndoCommandHandler(noteService, messageArgumentParser);

	@Test
	public void shouldSupportShowAction() throws Exception {
		//given
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.UNDO);
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
	public void shouldHandleUndoTitledNote() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("some-title", ""));
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is("Your last modification for the note *[some-title]* has been undone"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

	@Test
	public void shouldHandleUndoChannelNote() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("", ""));
		SlashCommand slashCommand = mock(SlashCommand.class);
		when(slashCommand.getChannelName()).thenReturn("some-channel-name");
		//when
		SlashCommandResult result = sut.handle(slashCommand);
		//then
		assertThat(result.getText(), is("Your last modification for the note *some-channel-name* has been undone"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}
}