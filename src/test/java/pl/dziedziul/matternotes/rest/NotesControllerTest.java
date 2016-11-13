package pl.dziedziul.matternotes.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.dziedziul.matternotes.command.CommandProcessor;
import pl.dziedziul.matternotes.domain.TestDataBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(NotesController.class)
public class NotesControllerTest {

	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CommandProcessor commandProcessor;

	@Test
	public void shouldProcessRequest() throws Exception {
		when(commandProcessor.process(any())).thenReturn(testDataBuilder.validSlashCommandResult());

		mvc.perform(post("/notes")
			.accept(MediaType.ALL))
			.andExpect(status().isOk())
			.andExpect(content().json(testDataBuilder.validSlashCommandResultAsJson()));
	}

}