package pl.dziedziul.matternotes.webhook;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteType;
import pl.dziedziul.matternotes.domain.TestDataBuilder;

public class NoteRendererTest {
	private NoteRenderer sut = new NoteRenderer();
	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Test
	public void shouldRenderNoNotes() throws Exception {
		//given
		//when
		//then
		assertThat(sut.renderNoNotes(), is("##### :pencil: No matching notes found :("));
	}

	@Test
	public void shouldRenderSingleNote() throws Exception {
		//given
		Note note = testDataBuilder.createValidNoteWithSingleMessage(NoteType.TITLED);
		//when
		String rendered = sut.render(note);
		//then
		assertThat(rendered, is("##### :pencil: Notes from *[title-1]*:\n" +
			"\n" +
			"* message-1\n"));
	}

	@Test
	public void shouldRenderMultipleNotes() throws Exception {
		//given
		Note note1 = testDataBuilder.createValidNoteWith2Message(NoteType.CHANNEL);
		Note note2 = testDataBuilder.createValidNoteWith2Message(NoteType.TITLED);
		String rendered = sut.render(Arrays.asList(note1, note2));
		//then
		assertThat(rendered, is("##### :pencil: Your all notes\n" +
			" Notes from *title-1*:\n" +
			"\n" +
			"* message-1\n" +
			"* second message\n" +
			"\n" +
			"---\n" +
			" Notes from *[title-1]*:\n" +
			"\n" +
			"* message-1\n" +
			"* second message\n"));
	}
}