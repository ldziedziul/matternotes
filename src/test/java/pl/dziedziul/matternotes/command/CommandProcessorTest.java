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
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class CommandProcessorTest {

	private CommandActionExtractor actionExtractor;

	@Before
	public void setup() {
		actionExtractor = mock(CommandActionExtractor.class);
		when(actionExtractor.extractAction(any())).thenReturn(Action.FEEDBACK);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailWithoutSupportingHandler() throws Exception {
		//given
		CommandHandler handler1 = mock(CommandHandler.class);
		when(handler1.isSupporting(any())).thenReturn(false);
		CommandHandler handler2 = mock(CommandHandler.class);
		when(handler2.isSupporting(any())).thenReturn(false);
		CommandProcessor sut = new CommandProcessor(actionExtractor, Arrays.asList(handler2, handler1));
		//when
		SlashCommandResult result = sut.process(mock(SlashCommand.class));
		//then
		//should fail
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

	@Test(expected = IllegalStateException.class)
	public void shouldAllowForEmptyHandlersList() throws Exception {
		//given
		//when
		CommandProcessor sut = new CommandProcessor(actionExtractor, Collections.emptyList());
		//then
		//should not fail
		sut.process(mock(SlashCommand.class));
	}

	@Test
	public void shouldHandleWithSupportingHandler() throws Exception {
		//given
		CommandHandler handler1 = mock(CommandHandler.class);
		when(handler1.isSupporting(any())).thenReturn(false);
		CommandHandler handler2 = mock(CommandHandler.class);
		when(handler2.isSupporting(any())).thenReturn(true);
		SlashCommandResult expectedCommandResult = TestDataBuilder.getInstance().validSlashCommandResult();
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