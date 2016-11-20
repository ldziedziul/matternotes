package pl.dziedziul.matternotes.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NoteTest {

	private Note sut;
	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Before
	public void setup() {
		sut = testDataBuilder.createValidNoteWith2Message(NoteType.CHANNEL);
	}

	@Test
	public void shouldAddMessage() throws Exception {
		//given
		assertMessagesCount(sut, 2);
		//when
		sut.addMessage("some-text");
		//then
		assertMessagesCount(sut, 3);
		assertThat(sut.getMessages().get(2).getText(), is("some-text"));
	}

	private void assertMessagesCount(Note note, int count) {
		assertThat(note.getMessages(), hasSize(count));
	}

	@Test
	public void shouldDeleteLastMessage() throws Exception {
		//given
		assertMessagesCount(sut, 2);
		//when
		sut.deleteLastMessage();
		//then
		assertMessagesCount(sut, 1);
		assertThat(sut.getMessages().get(0).getText(),is("message-1"));
	}

	@Test
	public void shouldGetLastMessage() throws Exception {
		//given
		//when
		//then
		assertThat(sut.getLastMessage().getText(), is("second message"));

	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailGetLastMessageWithoutMessages() throws Exception {
		//given
		Note note = new Note();
		assertMessagesCount(note, 0);
		//when
		note.getLastMessage();
		//then
		//should fail
	}

}