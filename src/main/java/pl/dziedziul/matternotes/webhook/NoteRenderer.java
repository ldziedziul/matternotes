package pl.dziedziul.matternotes.webhook;

import static java.util.stream.Collectors.*;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.dziedziul.matternotes.domain.Message;
import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteType;

@Component
public class NoteRenderer {

	public String render(List<Note> notes) {
		return "##### :pencil: Your all notes\n" + notes.stream().map(this::renderSingleNote).collect(joining("\n---\n"));
	}

	public String renderNoNotes() {
		return "##### :pencil: No matching notes found :(";
	}

	public String render(Note note) {
		return "##### :pencil:" + renderSingleNote(note);
	}

	private String renderSingleNote(Note note) {
		return
			" Notes from *" + renderTitle(note) + "*:\n\n" +
				formateMessages(note.getMessages());
	}

	private String renderTitle(Note note) {
		return note.getType().equals(NoteType.TITLED) ? "[" + note.getTitle() + "]" : note.getTitle();
	}

	private String formateMessages(Collection<Message> messages) {
		return messages.stream().map(this::formatMessage).collect(joining());
	}

	private String formatMessage(Message m) {
		return MessageFormat.format("* {0}\n", m.getText());
	}

}
