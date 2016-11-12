package pl.dziedziul.matternotes.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import pl.dziedziul.matternotes.webhook.SlashCommand;

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