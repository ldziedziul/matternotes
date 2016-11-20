package pl.dziedziul.matternotes.command.handler;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteType;
import pl.dziedziul.matternotes.domain.service.NoteService;
import pl.dziedziul.matternotes.webhook.NoteRenderer;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

@Component
public class ShowCommandHandler extends ActionBasedCommandHandler {

	static final String ALL = "all";
	private final NoteService noteService;
	private final MessageArgumentParser messageArgumentParser;
	private final NoteRenderer noteRenderer;


	public ShowCommandHandler(NoteService noteService, MessageArgumentParser messageArgumentParser, NoteRenderer noteRenderer) {
		this.noteService = noteService;
		this.messageArgumentParser = messageArgumentParser;
		this.noteRenderer = noteRenderer;
	}

	@Override
	public SlashCommandResult handle(SlashCommand command) {
		MessageArguments args = messageArgumentParser.parse(command.getText());
		String text;
		if (requestedAllNotes(args)) {
			List<Note> allNotes = getAllNotes(command, args);
			text = renderAllNotes(allNotes);
		} else {
			text = getSingleNote(command, args)
				.map(noteRenderer::render)
				.orElseGet(noteRenderer::renderNoNotes);
		}
		return new SlashCommandResult(text);
	}

	private String renderAllNotes(List<Note> allNotes) {
		String text;
		if (allNotes.isEmpty()) {
			text = noteRenderer.renderNoNotes();
		} else {
			text = noteRenderer.render(allNotes);
		}
		return text;
	}

	private boolean requestedAllNotes(MessageArguments args) {
		return args.hasText() && args.getText().equals(ALL);
	}


	private List<Note> getAllNotes(SlashCommand command, MessageArguments args) {
		return noteService.getAllNotes(command.getUserId());
	}

	private Optional<Note> getSingleNote(SlashCommand command, MessageArguments args) {
		NoteSearchParams searchParams = new NoteSearchParams();
		searchParams.setTitle(args.getTitle());
		searchParams.setUserId(command.getUserId());
		searchParams.setType(args.hasTitle() ? NoteType.TITLED : NoteType.CHANNEL);
		searchParams.setChannelId(command.getChannelId());
		return noteService.getNote(searchParams);
	}

	@Override
	protected Action getSupportedAction() {
		return Action.SHOW;
	}
}
