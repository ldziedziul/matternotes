package pl.dziedziul.matternotes.webhook;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.TestDataBuilder;

public class SlashCommandTest {

	private static final String VALUE = "some-value";
	private TestDataBuilder testDataBuilder;

	@Test
	public void shouldFulfillEqualsContract() {
		EqualsVerifier.forClass(SlashCommand.class)
			.suppress(Warning.NONFINAL_FIELDS)
			.suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
			.withRedefinedSuperclass()
			.suppress(Warning.STRICT_INHERITANCE)
			.verify();
	}

	@Test
	public void shouldBuildCommand() throws Exception {
		//given
		SlashCommand slashCommand = SlashCommand.Builder.newSlashCommand().build();
		//when
		//then
		checkCustomSetter(slashCommand::setChannel_id, slashCommand::getChannelId, VALUE);
		checkCustomSetter(slashCommand::setChannelId, slashCommand::getChannelId, VALUE);
		checkCustomSetter(slashCommand::setChannel_name, slashCommand::getChannelName, VALUE);
		checkCustomSetter(slashCommand::setChannelName, slashCommand::getChannelName, VALUE);
		checkCustomSetter(slashCommand::setResponse_url, slashCommand::getResponseUrl, VALUE);
		checkCustomSetter(slashCommand::setResponseUrl, slashCommand::getResponseUrl, VALUE);
		checkCustomSetter(slashCommand::setTeam_domain, slashCommand::getTeamDomain, VALUE);
		checkCustomSetter(slashCommand::setTeamDomain, slashCommand::getTeamDomain, VALUE);
		checkCustomSetter(slashCommand::setTeam_id, slashCommand::getTeamId, VALUE);
		checkCustomSetter(slashCommand::setTeamId, slashCommand::getTeamId, VALUE);
		checkCustomSetter(slashCommand::setUser_id, slashCommand::getUserId, VALUE);
		checkCustomSetter(slashCommand::setUserId, slashCommand::getUserId, VALUE);
		checkCustomSetter(slashCommand::setUser_name, slashCommand::getUsername, VALUE);
		checkCustomSetter(slashCommand::setUsername, slashCommand::getUsername, VALUE);
		checkCustomSetter(slashCommand::setCommand, slashCommand::getCommand, VALUE);
		checkCustomSetter(slashCommand::setText, slashCommand::getText, VALUE);
		checkCustomSetter(slashCommand::setToken, slashCommand::getToken, VALUE);
		checkCustomSetter(slashCommand::setAction, slashCommand::getAction, Action.CLEAR);
	}

	private <T> void checkCustomSetter(Consumer<T> setter, Supplier<T> getter, T value) {
		assertNull(getter.get());
		setter.accept(value);
		assertThat(getter.get(), is(value));
		setter.accept(null);
		assertThat(getter.get(), is(nullValue()));
	}

	@Test
	public void shouldToString() throws Exception {
		//given
		testDataBuilder = TestDataBuilder.getInstance();
		SlashCommand slashCommand = testDataBuilder.validSlashCommand();
		//when
		assertThat(slashCommand.toString(), is("SlashCommand[channelId=some-channel-id,channelName=some channel name,command=/some-command,responseUrl=some-response-url,teamDomain=some-team-domain,teamId=some-team-id,text=Some sample text,token=some-token,userId=some-user-id,username=some user name]"));
		//then

	}
}