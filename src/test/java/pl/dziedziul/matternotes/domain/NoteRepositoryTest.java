package pl.dziedziul.matternotes.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryTest {

	@Autowired
	private NoteRepository noteRepository;

	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Test
	public void shouldSaveNoteWithDateCreatedFilled() throws Exception {
		//given
		assertNotesCount(0);

		Note note = testDataBuilder.createValidNote("1", "some-user-id", NoteType.CHANNEL);
		//when
		save(note);
		//then
		assertNotNull(note.getId());
		assertNotNull(note.getDateCreated());
		assertNotesCount(1);

	}

	@Test
	public void shouldFindTitledNote() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByTitleAndUserIdAndType("title-1", "some-user-id", NoteType.TITLED);
		//then
		assertTrue(result.isPresent());
		Note note = result.get();
		assertThat(note.getTitle(), is("title-1"));
		assertThat(note.getUserId(), is("some-user-id"));
		assertThat(note.getType(), is(NoteType.TITLED));
	}

	@Test
	public void shouldNotFindTitledNoteWithWrongTitle() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByTitleAndUserIdAndType("title-3", "some-user-id", NoteType.TITLED);
		//then
		assertFalse(result.isPresent());
	}

	@Test
	public void shouldNotFindTitledNoteWithWrongUserId() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByTitleAndUserIdAndType("title-1", "some-wrong-id", NoteType.TITLED);
		//then
		assertFalse(result.isPresent());
	}

	@Test
	public void shouldNotFindTitledNoteWithWrongType() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByTitleAndUserIdAndType("title-1", "some-other-user-id", NoteType.CHANNEL);
		//then
		assertFalse(result.isPresent());
	}

	@Test
	public void shouldFindChannelNote() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByChannelIdAndUserIdAndType("channelId-1", "some-user-id", NoteType.CHANNEL);
		//then
		assertTrue(result.isPresent());
		Note note = result.get();
		assertThat(note.getTitle(), is("title-1"));
		assertThat(note.getChannelId(), is("channelId-1"));
		assertThat(note.getUserId(), is("some-user-id"));
		assertThat(note.getType(), is(NoteType.CHANNEL));
	}

	@Test
	public void shouldNotFindChannelNoteWithWrongChannelId() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByChannelIdAndUserIdAndType("wrong-channelId", "some-user-id", NoteType.CHANNEL);
		//then
		assertFalse(result.isPresent());
	}

	@Test
	public void shouldNotFindChannelNoteWithWrongUserId() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByChannelIdAndUserIdAndType("channelId-1", "some-wrong-id", NoteType.CHANNEL);
		//then
		assertFalse(result.isPresent());
	}

	@Test
	public void shouldNotFindChannelNoteWithWrongType() throws Exception {
		//given
		prepareSampleNotes();
		//when
		Optional<Note> result = noteRepository.findByTitleAndUserIdAndType("channelId-1", "some-other-user-id", NoteType.TITLED);
		//then
		assertFalse(result.isPresent());
	}

	private void prepareSampleNotes() {
		save(testDataBuilder.createValidNote("1", "some-user-id", NoteType.CHANNEL));
		save(testDataBuilder.createValidNote("1", "some-user-id", NoteType.TITLED));
		save(testDataBuilder.createValidNote("1", "some-other-user-id", NoteType.TITLED));
		save(testDataBuilder.createValidNote("2", "some-user-id", NoteType.TITLED));
		save(testDataBuilder.createValidNote("2", "some-user-id", NoteType.CHANNEL));
		save(testDataBuilder.createValidNote("2", "some-other-user-id", NoteType.CHANNEL));
	}

	private void save(Note note) {
		noteRepository.save(note);
	}

	private void assertNotesCount(int expectedCount) {
		assertThat(noteRepository.findAll(), hasSize(expectedCount));
	}
}