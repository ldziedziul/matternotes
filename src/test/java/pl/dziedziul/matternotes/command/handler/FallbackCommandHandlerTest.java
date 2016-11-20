package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.dziedziul.matternotes.domain.TestDataBuilder;
import pl.dziedziul.matternotes.webhook.ResponseType;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class FallbackCommandHandlerTest {

	private final TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Test
	public void shouldIsSupporting() throws Exception {
		//given
		FallbackCommandHandler sut = new FallbackCommandHandler();
		//when
		//then
		assertTrue(sut.isSupporting(mock(SlashCommand.class)));

	}

	@Test
	public void shouldHandle() throws Exception {
		//given
		FallbackCommandHandler sut = new FallbackCommandHandler();
		//when
		SlashCommand slashCommand = testDataBuilder.validSlashCommand();
		SlashCommandResult result = sut.handle(slashCommand);
		//then
		assertThat(result.getText(), is("Unknown command: Some sample text"));
		assertThat(result.getResponseType(), is(ResponseType.EPHEMERAL));
	}

}