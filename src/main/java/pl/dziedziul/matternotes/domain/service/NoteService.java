package pl.dziedziul.matternotes.domain.service;


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
		if (note.getType().equals(NoteType.CHANNEL)) {
			persistetNote = noteRepository.findByChannelIdAndUserIdAndType(note.getChannelId(), note.getUserId(), NoteType.CHANNEL);
		} else {
			persistetNote = noteRepository.findByTitleAndUserIdAndType(note.getTitle(), note.getUserId(), NoteType.TITLED);
		}
		if (persistetNote.isPresent()) {
			persistetNote.get().addMessage(note.getLastMessage().getText());
			return UpsertNoteResult.UPDATED;
		} else {
			noteRepository.save(note);
			return UpsertNoteResult.INSERTED;
		}
	}
}
