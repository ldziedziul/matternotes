package pl.dziedziul.matternotes.domain.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.dziedziul.matternotes.command.handler.NoteSearchParams;
import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteRepository;
import pl.dziedziul.matternotes.domain.NoteType;

@Service
public class NoteService {

	private final NoteRepository noteRepository;

	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	@Transactional
	public UpsertNoteResult upsertNote(Note note) {
		Optional<Note> persistetNote;
		persistetNote = findNote(NoteSearchParams.of(note));
		if (persistetNote.isPresent()) {
			persistetNote.get().addMessage(note.getLastMessage().getText());
			return UpsertNoteResult.UPDATED;
		} else {
			noteRepository.save(note);
			return UpsertNoteResult.INSERTED;
		}
	}

	private Optional<Note> findNote(NoteSearchParams searchParams) {
		Optional<Note> persistetNote;
		if (searchParams.getType().equals(NoteType.CHANNEL)) {
			persistetNote = noteRepository.findByChannelIdAndUserIdAndType(searchParams.getChannelId(), searchParams.getUserId(), searchParams.getType());
		} else {
			persistetNote = noteRepository.findByTitleAndUserIdAndType(searchParams.getTitle(), searchParams.getUserId(), searchParams.getType());
		}
		return persistetNote;
	}

	@Transactional(readOnly = true)
	public Optional<Note> getNote(NoteSearchParams searchParams) {
		return findNote(searchParams);
	}

	public List<Note> getAllNotes(String userId) {
		return noteRepository.findAllByUserIdOrderByTitle(userId);
	}

	public void deleteByExample(NoteSearchParams searchParams) {
		findNote(searchParams).ifPresent(noteRepository::delete);
	}
}
