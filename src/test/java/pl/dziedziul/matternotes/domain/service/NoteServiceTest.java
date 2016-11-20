package pl.dziedziul.matternotes.domain.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Test;

import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteRepository;
import pl.dziedziul.matternotes.domain.NoteType;
import pl.dziedziul.matternotes.domain.TestDataBuilder;

public class NoteServiceTest {
	private TestDataBuilder testDataBuilder = TestDataBuilder.getInstance();

	@Test
	public void shouldInsertTitledNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		when(noteRepository.findByTitleAndUserIdAndType(any(), any(), eq(NoteType.TITLED))).thenReturn(Optional.empty());
		//when
		Note noteToSave = testDataBuilder.createValidNoteWithSingleMessage(NoteType.TITLED);
		UpsertNoteResult result = noteService.upsertNote(noteToSave);
		//then
		assertThat(result, is(UpsertNoteResult.INSERTED));
		verify(noteRepository).save(noteToSave);
	}

	@Test
	public void shouldUpdateTitledNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedTitleNote(noteRepository);
		//when
		Note noteToSave = testDataBuilder.createValidNoteWithSingleMessage(NoteType.TITLED);
		UpsertNoteResult result = noteService.upsertNote(noteToSave);
		//then
		assertThat(result, is(UpsertNoteResult.UPDATED));
		assertThat(persistedNote.getMessages(), hasSize(1));
	}

	@Test
	public void shouldInsertChannelNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		when(noteRepository.findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL))).thenReturn(Optional.empty());
		//when
		Note noteToSave = testDataBuilder.createValidNoteWithSingleMessage(NoteType.CHANNEL);
		UpsertNoteResult result = noteService.upsertNote(noteToSave);
		//then
		assertThat(result, is(UpsertNoteResult.INSERTED));
		verify(noteRepository).save(noteToSave);
	}

	@Test
	public void shouldUpdateChannelNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedChannelNote(noteRepository);
		//when
		Note noteToSave = testDataBuilder.createValidNoteWithSingleMessage(NoteType.CHANNEL);
		UpsertNoteResult result = noteService.upsertNote(noteToSave);
		//then
		assertThat(result, is(UpsertNoteResult.UPDATED));
		assertThat(persistedNote.getMessages(), hasSize(1));
	}


	@Test
	public void shouldFindChannelNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedChannelNote(noteRepository);
		//when
		Optional<Note> result = noteService.getNoteByExample(createNoteSearchParams(NoteType.CHANNEL));
		//then
		assertTrue(result.isPresent());
		assertThat(result.get(), is(persistedNote));
		verify(noteRepository).findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL));
	}

	@Test
	public void shouldFindTitledNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedTitleNote(noteRepository);
		//when
		Optional<Note> result = noteService.getNoteByExample(createNoteSearchParams(NoteType.TITLED));
		//then
		assertTrue(result.isPresent());
		assertThat(result.get(), is(persistedNote));
		verify(noteRepository).findByTitleAndUserIdAndType(any(), any(), eq(NoteType.TITLED));
	}

	private Note createNoteSearchParams(NoteType noteType) {
		Note note = new Note();
		note.setType(noteType);
		return note;
	}

	private Note setupPersistedChannelNote(NoteRepository noteRepository) {
		Note persistedNote = new Note();
		when(noteRepository.findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL))).thenReturn(Optional.of(persistedNote));
		return persistedNote;
	}

	private Note setupPersistedTitleNote(NoteRepository noteRepository) {
		Note persistedNote = new Note();
		when(noteRepository.findByTitleAndUserIdAndType(any(), any(), eq(NoteType.TITLED))).thenReturn(Optional.of(persistedNote));
		return persistedNote;
	}
}