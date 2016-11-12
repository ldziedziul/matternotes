package pl.dziedziul.matternotes.webhook;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class CommandProcessorTest {
	@Test
	public void shouldProcess() throws Exception {
		//given
		CommandActionExtractor actionExtractor = mock(CommandActionExtractor.class);
		when(actionExtractor.extractAction(any())).thenReturn(Action.FEEDBACK);
		CommandProcessor sut = new CommandProcessor(actionExtractor);
		//when
		SlashCommandResult result = sut.process(mock(SlashCommand.class));
		//then
		assertThat(result.getText(), is("Dummy response *text* :+1: with action FEEDBACK"));
	}

}