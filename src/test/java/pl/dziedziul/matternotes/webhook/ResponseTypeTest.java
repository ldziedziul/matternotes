package pl.dziedziul.matternotes.webhook;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class ResponseTypeTest {

	@DataProvider
	public static Object[][] fromStringData() {
		return new Object[][] {
			{ "in_channel", ResponseType.IN_CHANNEL }, //
			{ "ephemeral", ResponseType.EPHEMERAL }, //
			{ "", null }, //
			{ null, null }, //
		};
	}

	@Test
	@UseDataProvider("fromStringData")
	public void shouldFromString(String key, ResponseType expectedValue) throws Exception {
		//given
		//when
		ResponseType result = ResponseType.fromString(key);
		//then
		assertThat(result, is(expectedValue));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWithInvalidStringValue() throws Exception {
		//given
		//when
		ResponseType.fromString("invalid-value");
		//then
		//should fail
	}

}