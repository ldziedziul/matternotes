package pl.dziedziul.matternotes.webhook;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SlashCommandTest {

	@Test
	public void shouldFulfillEqualsContract() {
		EqualsVerifier.forClass(SlashCommand.class)
			.suppress(Warning.NONFINAL_FIELDS)
			.suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
			.withRedefinedSuperclass()
			.suppress(Warning.STRICT_INHERITANCE)
			.verify();
	}
}