package pl.dziedziul.matternotes.command.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class MessageArgumentsTest {

	@DataProvider
	public static Object[][] dataHasTitle() {
		return new Object[][] {
			{ "some-title", "some-text", true },
			{ "", "some-text", false },
			{ "some-title", "", true },
			{ "", "", false }
		};
	}

	@DataProvider
	public static Object[][] dataHasText() {
		return new Object[][] {
			{ "some-title", "some-text", true },
			{ "", "some-text", true },
			{ "some-title", "", false },
			{ "", "", false }
		};
	}

	@Test
	@UseDataProvider("dataHasTitle")
	public void shouldHasTitle(String title, String text, boolean expected) throws Exception {
		//given
		MessageArguments arguments = new MessageArguments(title, text);
		//when
		//then
		assertThat(arguments.hasTitle(), is(expected));
	}

	@Test
	@UseDataProvider("dataHasText")
	public void shouldHasText(String title, String text, boolean expected) throws Exception {
		//given
		MessageArguments arguments = new MessageArguments(title, text);
		//when
		//then
		assertThat(arguments.hasText(), is(expected));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectNullTitle() throws Exception {
		//given
		//when
		new MessageArguments(null, "some-text");
		//then
		//should fail
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectNullText() throws Exception {
		//given
		//when
		new MessageArguments("some-title", null);
		//then
		//should fail
	}
}