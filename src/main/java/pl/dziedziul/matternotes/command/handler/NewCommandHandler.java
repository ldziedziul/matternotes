package pl.dziedziul.matternotes.command.handler;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteType;
import pl.dziedziul.matternotes.domain.service.NoteService;
import pl.dziedziul.matternotes.domain.service.UpsertNoteResult;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

@Component
public class NewCommandHandler extends ActionBasedCommandHandler {

	private final NoteService noteService;
	private final MessageArgumentParser messageArgumentParser;

	public NewCommandHandler(NoteService noteService, MessageArgumentParser messageArgumentParser) {
		this.noteService = noteService;
		this.messageArgumentParser = messageArgumentParser;
	}

	@Override
	public SlashCommandResult handle(SlashCommand command) {
		Note note = createNote(command);
		UpsertNoteResult result = noteService.upsertNote(note);
		String resultMessage = createResultMessage(note, result);
		return new SlashCommandResult(resultMessage);
	}

	private String createResultMessage(Note note, UpsertNoteResult result) {
		String actionLabel = getActionLabel(result);
		String readCommand = createReadCommand(note);
		return MessageFormat.format(":pencil: Note {0}. Run `{1}` to read it", actionLabel, readCommand);
	}

	private String getActionLabel(UpsertNoteResult result) {
		String actionLabel;
		switch (result) {
			case INSERTED:
				actionLabel = "created";
				break;
			case UPDATED:
				actionLabel = "updated";
				break;
			default:
				throw new IllegalArgumentException("Unsupported result value: " + result);
		}
		return actionLabel;
	}

	private String createReadCommand(Note note) {
		return note.getType().equals(NoteType.CHANNEL) ? "/notes show" : "/notes show [" + note.getTitle() + "]";
	}

	private Note createNote(SlashCommand command) {
		Note note = new Note();
		MessageArguments args = messageArgumentParser.parse(command.getText());
		boolean hasTitle = args.hasTitle();
		note.setTitle(hasTitle ? args.getTitle() : command.getChannelName());//will it be filled in case of direct messages?
		note.setUsername(command.getUsername());
		note.setUserId(command.getUserId());
		note.setType(hasTitle ? NoteType.TITLED : NoteType.CHANNEL);
		note.setChannelName(command.getChannelName());
		note.setChannelId(command.getChannelId());
		note.addMessage(args.getText());
		return note;
	}

	@Override
	protected Action getSupportedAction() {
		return Action.NEW;
	}

}
