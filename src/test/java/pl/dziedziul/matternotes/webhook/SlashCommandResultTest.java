package pl.dziedziul.matternotes.webhook;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import pl.dziedziul.matternotes.mapper.JsonMapper;
import pl.dziedziul.matternotes.domain.TestDataBuilder;

public class SlashCommandResultTest {

	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();
	private JsonMapper jsonMapper = new JsonMapper();

	@Test
	public void shouldDeserializeFromJson() throws Exception {
		//given
		//when
		SlashCommandResult commandResult = jsonMapper.fromJson(sampleCommandResultJson(), SlashCommandResult.class);
		//then
		assertNotNull(commandResult);
		assertThat(commandResult.getText(), is("some text"));
	}

	private String sampleCommandResultJson() {
		return testDataBuilder.validSlashCommandResultAsJson();
	}

	@Test
	public void shouldSerializeToJson() {

		SlashCommandResult command = testDataBuilder.validSlashCommandResult();
		String json = new JsonMapper().toJson(command);
		assertThat(json, is(sampleCommandResultJson()));
	}

	@Test
	public void shouldRoundRobinJson() {
		JsonMapper jsonMapper = new JsonMapper();
		SlashCommandResult command = jsonMapper.fromJson(sampleCommandResultJson(), SlashCommandResult.class);
		String json = jsonMapper.toJson(command);
		assertThat(json, is(sampleCommandResultJson()));
	}

	@Test
	public void shouldFulfillEqualsContract() {
		EqualsVerifier.forClass(SlashCommandResult.class)
			.suppress(Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.suppress(Warning.STRICT_INHERITANCE)
			.verify();
	}
}