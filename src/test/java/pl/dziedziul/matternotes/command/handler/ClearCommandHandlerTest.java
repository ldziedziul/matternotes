package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.service.NoteService;
import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class ClearCommandHandlerTest {
	private MessageArgumentParser messageArgumentParser = mock(MessageArgumentParser.class);
	private NoteService noteService = mock(NoteService.class);
	private ClearCommandHandler sut = new ClearCommandHandler(noteService, messageArgumentParser);

	@Test
	public void shouldSupportShowAction() throws Exception {
		//given
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.CLEAR);
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
	public void shouldHandleDeletionTitledNote() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("some-title", ""));
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is("All your notes from *[some-title]* have been removed"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

	@Test
	public void shouldHandleDeletionChannelNote() throws Exception {
		//given
		when(messageArgumentParser.parse(anyString())).thenReturn(new MessageArguments("", ""));
		SlashCommand slashCommand = mock(SlashCommand.class);
		when(slashCommand.getChannelName()).thenReturn("some-channel-name");
		//when
		SlashCommandResult result = sut.handle(slashCommand);
		//then
		assertThat(result.getText(), is("All your notes from *some-channel-name* have been removed"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}
}