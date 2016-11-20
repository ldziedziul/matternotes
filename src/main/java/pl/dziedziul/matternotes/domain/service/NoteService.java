package pl.dziedziul.matternotes.domain.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		persistetNote = findNote(note);
		if (persistetNote.isPresent()) {
			persistetNote.get().addMessage(note.getLastMessage().getText());
			return UpsertNoteResult.UPDATED;
		} else {
			noteRepository.save(note);
			return UpsertNoteResult.INSERTED;
		}
	}

	private Optional<Note> findNote(Note note) {
		Optional<Note> persistetNote;
		if (note.getType().equals(NoteType.CHANNEL)) {
			persistetNote = noteRepository.findByChannelIdAndUserIdAndType(note.getChannelId(), note.getUserId(), note.getType());
		} else {
			persistetNote = noteRepository.findByTitleAndUserIdAndType(note.getTitle(), note.getUserId(), note.getType());
		}
		return persistetNote;
	}

	@Transactional(readOnly = true)
	public Optional<Note> getNoteByExample(Note noteExample) {
		return findNote(noteExample);
	}

	public List<Note> getAllNotes(String userId) {
		return noteRepository.findAllByUserIdOrderByTitle(userId);
	}
}
