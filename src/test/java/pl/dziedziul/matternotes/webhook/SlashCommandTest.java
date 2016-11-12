package pl.dziedziul.matternotes.webhook;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SlashCommandTest {

	@Test
	public void shouldFulfillEqualsContract() {
		EqualsVerifier.forClass(SlashCommand.class)
			.suppress(Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.suppress(Warning.STRICT_INHERITANCE)
			.verify();
	}
}