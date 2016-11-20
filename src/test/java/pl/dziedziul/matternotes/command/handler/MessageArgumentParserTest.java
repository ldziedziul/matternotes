package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class MessageArgumentParserTest {
	private MessageArgumentParser sut = new MessageArgumentParser();

	@DataProvider
	public static Object[][] data() {
		return new Object[][] {
			{ "my private note", new MessageArguments("", "my private note") },//
			{ "[title] my private note", new MessageArguments("title", "my private note") },//
			{ "[title with spaces] my private note", new MessageArguments("title with spaces", "my private note") },//
			{ "[] my private note", new MessageArguments("", "my private note") },//
			{ "", new MessageArguments("", "") },//
			{ "show", new MessageArguments("", "") },//
			{ "show [title]", new MessageArguments("title", "") },//
			{ "show all", new MessageArguments("", "all") },//
			{ "clear", new MessageArguments("", "") },//
			{ "clear [title]", new MessageArguments("title", "") },//
			{ "undo", new MessageArguments("", "") },//
			{ "undo [title]", new MessageArguments("title", "") },//
			{ "help", new MessageArguments("", "") },//
			{ "feedback", new MessageArguments("", "") },//
			{ null, new MessageArguments("", "") },//
		};
	}

	@Test
	@UseDataProvider(("data"))
	public void shouldParse(String text, MessageArguments expectedMessageArguments) throws Exception {
		//given
		//when
		MessageArguments result = sut.parse(text);
		//then
		assertThat(result, is(expectedMessageArguments));

	}

}