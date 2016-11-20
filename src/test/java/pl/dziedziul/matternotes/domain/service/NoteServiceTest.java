package pl.dziedziul.matternotes.domain.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import pl.dziedziul.matternotes.command.handler.NoteSearchParams;
import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteRepository;
import pl.dziedziul.matternotes.domain.NoteType;
import pl.dziedziul.matternotes.domain.TestDataBuilder;

public class NoteServiceTest {
	private static final String SOME_USER_ID = "some-user-id";
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
		assertThat(persistedNote.getMessages(), hasSize(1));
		//when
		Note noteToSave = testDataBuilder.createValidNoteWithSingleMessage(NoteType.CHANNEL);
		UpsertNoteResult result = noteService.upsertNote(noteToSave);
		//then
		assertThat(result, is(UpsertNoteResult.UPDATED));
		assertThat(persistedNote.getMessages(), hasSize(2));
	}


	@Test
	public void shouldFindChannelNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedChannelNote(noteRepository);
		//when
		Optional<Note> result = noteService.getNote(createNoteSearchParams(NoteType.CHANNEL));
		//then
		assertTrue(result.isPresent());
		assertThat(result.get(), is(persistedNote));
		verify(noteRepository).findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL));
	}

	@Test
	public void shouldFindAllNotes() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		List<Note> persistedNotes = Collections.unmodifiableList(Arrays.asList(new Note(), new Note()));
		when(noteRepository.findAllByUserIdOrderByTitle(SOME_USER_ID)).thenReturn(persistedNotes);
		//when
		List<Note> allNotes = noteService.getAllNotes(SOME_USER_ID);
		//then
		assertNotNull(allNotes);
		assertThat(allNotes, is(persistedNotes));
		verify(noteRepository).findAllByUserIdOrderByTitle(SOME_USER_ID);
	}

	@Test
	public void shouldFindTitledNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedTitleNote(noteRepository);
		//when
		Optional<Note> result = noteService.getNote(createNoteSearchParams(NoteType.TITLED));
		//then
		assertTrue(result.isPresent());
		assertThat(result.get(), is(persistedNote));
		verify(noteRepository).findByTitleAndUserIdAndType(any(), any(), eq(NoteType.TITLED));
	}

	private NoteSearchParams createNoteSearchParams(NoteType noteType) {
		NoteSearchParams searchParams = new NoteSearchParams();
		searchParams.setType(noteType);
		return searchParams;
	}

	private Note setupPersistedChannelNote(NoteRepository noteRepository) {
		Note persistedNote = new Note();
		persistedNote.addMessage("some-message");
		when(noteRepository.findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL))).thenReturn(Optional.of(persistedNote));
		return persistedNote;
	}

	private Note setupPersistedTitleNote(NoteRepository noteRepository) {
		Note persistedNote = new Note();
		when(noteRepository.findByTitleAndUserIdAndType(any(), any(), eq(NoteType.TITLED))).thenReturn(Optional.of(persistedNote));
		return persistedNote;
	}

	@Test
	public void shouldDeleteNote() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedChannelNote(noteRepository);
		//when
		noteService.deleteByExample(createNoteSearchParams(NoteType.CHANNEL));
		//then
		verify(noteRepository).delete(persistedNote);
	}

	@Test
	public void shouldIgnoreNonexistingNoteWhenDeleting() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		when(noteRepository.findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL))).thenReturn(Optional.empty());
		//when
		noteService.deleteByExample(createNoteSearchParams(NoteType.CHANNEL));
		//then
		verify(noteRepository).findByChannelIdAndUserIdAndType(any(), any(), any());
		verifyNoMoreInteractions(noteRepository);
	}

	@Test
	public void shouldDeleteNoteWithSingleMessageWhenUndoLastModification() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedChannelNote(noteRepository);
		//when
		noteService.undoLastModification(createNoteSearchParams(NoteType.CHANNEL));
		//then
		verify(noteRepository).delete(persistedNote);

	}

	@Test
	public void shouldDeleteMessageFromNoteWithMultipleMessagesWhenUndoLastModification() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		Note persistedNote = setupPersistedChannelNote(noteRepository);
		persistedNote.addMessage("second-message");
		//when
		noteService.undoLastModification(createNoteSearchParams(NoteType.CHANNEL));
		//then
		verify(noteRepository, never()).delete(persistedNote);
	}

	@Test
	public void shouldIgnoreNonexistingNoteWhenUndoing() throws Exception {
		//given
		NoteRepository noteRepository = mock(NoteRepository.class);
		NoteService noteService = new NoteService(noteRepository);
		when(noteRepository.findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL))).thenReturn(Optional.empty());
		//when
		noteService.undoLastModification(createNoteSearchParams(NoteType.CHANNEL));
		//then
		verify(noteRepository).findByChannelIdAndUserIdAndType(any(), any(), any());
		verifyNoMoreInteractions(noteRepository);
	}

}