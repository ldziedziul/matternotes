package pl.dziedziul.matternotes.command;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import pl.dziedziul.matternotes.command.handler.CommandHandler;
import pl.dziedziul.matternotes.domain.TestDataBuilder;
import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class CommandProcessorTest {

	private CommandActionExtractor actionExtractor;
	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Before
	public void setup() {
		actionExtractor = mock(CommandActionExtractor.class);
		when(actionExtractor.extractAction(any())).thenReturn(Action.ABOUT);
	}

	@Test
	public void shouldFallbackWithoutSupportingHandler() throws Exception {
		//given
		CommandHandler handler1 = mock(CommandHandler.class);
		when(handler1.isSupporting(any())).thenReturn(false);
		CommandHandler handler2 = mock(CommandHandler.class);
		when(handler2.isSupporting(any())).thenReturn(false);
		CommandProcessor sut = new CommandProcessor(actionExtractor, Arrays.asList(handler2, handler1));
		//when
		SlashCommandResult result = sut.process(testDataBuilder.validSlashCommand());
		//then
		assertThat(result.getText(), is("Unknown command: Some sample text"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWithoutHandlers() throws Exception {
		//given
		//when
		new CommandProcessor(actionExtractor, null);
		//then
		//should fail
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWithoutActionExtractor() throws Exception {
		//given
		//when
		new CommandProcessor(null, Collections.emptyList());
		//then
		//should fail
	}

	@Test
	public void shouldAllowForEmptyHandlersList() throws Exception {
		//given
		CommandProcessor sut = new CommandProcessor(actionExtractor, Collections.emptyList());
		//when
		SlashCommandResult result = sut.process(testDataBuilder.validSlashCommand());
		//then
		assertThat(result.getText(), is("Unknown command: Some sample text"));
	}

	@Test
	public void shouldHandleWithSupportingHandler() throws Exception {
		//given
		CommandHandler handler1 = mock(CommandHandler.class);
		when(handler1.isSupporting(any())).thenReturn(false);
		CommandHandler handler2 = mock(CommandHandler.class);
		when(handler2.isSupporting(any())).thenReturn(true);
		SlashCommandResult expectedCommandResult = testDataBuilder.validSlashCommandResult();
		when(handler2.handle(any())).thenReturn(expectedCommandResult);
		CommandProcessor sut = new CommandProcessor(actionExtractor, Arrays.asList(handler1, handler2));
		//when
		SlashCommand command = mock(SlashCommand.class);
		SlashCommandResult result = sut.process(command);
		//then
		verify(handler1).isSupporting(command);
		verify(handler2).isSupporting(command);
		verify(handler2).handle(command);
		assertThat(result, is(expectedCommandResult));
	}

}