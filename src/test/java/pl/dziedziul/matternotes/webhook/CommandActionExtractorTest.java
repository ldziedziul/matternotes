package pl.dziedziul.matternotes.webhook;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class CommandActionExtractorTest {

	@DataProvider
	public static Object[][] data() {
		return new Object[][] {
			{ "my private note Records", Action.NEW }, //
			{ "[title] my private note Records", Action.NEW }, //
			{ "list", Action.LIST }, //
			{ "list all", Action.LIST }, //
			{ "clear", Action.CLEAR }, //
			{ "undo", Action.UNDO }, //
			{ "undo [title]", Action.UNDO }, //
			{ "help", Action.HELP }, //
			{ "feedback", Action.FEEDBACK }, //
			{ "", Action.HELP }, //
		};
	}

	@Test
	@UseDataProvider("data")
	public void shouldExtractAction(String message, Action expectedAction) throws Exception {
		//given
		CommandActionExtractor sut = new CommandActionExtractor();
		//when
		Action result = sut.extractAction(commandWithText(message));
		//then
		assertThat(result, is(expectedAction));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWithNullExtractAction() throws Exception {
		//given
		CommandActionExtractor sut = new CommandActionExtractor();
		//when
		Action result = sut.extractAction(commandWithText(null));
		//then

	}

	private SlashCommand commandWithText(String message) {
		SlashCommand slashCommand = new SlashCommand();
		slashCommand.setText(message);
		return slashCommand;
	}

}