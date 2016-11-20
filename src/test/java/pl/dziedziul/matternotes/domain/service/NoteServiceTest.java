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
		Note persistedNote = new Note();
		assertThat(persistedNote.getMessages(), is(empty()));
		when(noteRepository.findByTitleAndUserIdAndType(any(), any(), eq(NoteType.TITLED))).thenReturn(Optional.of(persistedNote));
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
		Note persistedNote = new Note();
		assertThat(persistedNote.getMessages(), is(empty()));
		when(noteRepository.findByChannelIdAndUserIdAndType(any(), any(), eq(NoteType.CHANNEL))).thenReturn(Optional.of(persistedNote));
		//when
		Note noteToSave = testDataBuilder.createValidNote(NoteType.CHANNEL);
		UpsertNoteResult result = noteService.upsertNote(noteToSave);
		//then
		assertThat(result, is(UpsertNoteResult.UPDATED));
		assertThat(persistedNote.getMessages(), hasSize(1));
	}

}