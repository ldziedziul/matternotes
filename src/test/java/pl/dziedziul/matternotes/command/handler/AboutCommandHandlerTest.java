package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class AboutCommandHandlerTest {

	@Test
	public void shouldSupportedAboutAction() throws Exception {
		//given
		AboutCommandHandler sut = new AboutCommandHandler();
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.ABOUT);
		boolean supporting = sut.isSupporting(command);
		//then
		assertTrue(supporting);
	}

	@Test
	public void shouldNotSupportedOtherAction() throws Exception {
		//given
		AboutCommandHandler sut = new AboutCommandHandler();
		//when
		SlashCommand command = mock(SlashCommand.class);
		when(command.getAction()).thenReturn(Action.HELP);
		boolean supporting = sut.isSupporting(command);
		//then
		assertFalse(supporting);
	}

	@Test
	public void shouldHandle() throws Exception {
		//given
		AboutCommandHandler sut = new AboutCommandHandler();
		//when
		SlashCommandResult result = sut.handle(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), containsString("github.com"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

}