package pl.dziedziul.matternotes.command.handler;

import org.springframework.stereotype.Component;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.domain.NoteType;
import pl.dziedziul.matternotes.domain.service.NoteService;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

@Component
public class ClearCommandHandler extends ActionBasedCommandHandler {

	private final NoteService noteService;
	private final MessageArgumentParser messageArgumentParser;

	public ClearCommandHandler(NoteService noteService, MessageArgumentParser messageArgumentParser) {
		this.noteService = noteService;
		this.messageArgumentParser = messageArgumentParser;
	}

	@Override
	public SlashCommandResult handle(SlashCommand command) {
		MessageArguments args = messageArgumentParser.parse(command.getText());
		NoteSearchParams searchParams = new NoteSearchParams();
		searchParams.setTitle(args.getTitle());
		searchParams.setUserId(command.getUserId());
		searchParams.setType(args.hasTitle() ? NoteType.TITLED : NoteType.CHANNEL);
		searchParams.setChannelId(command.getChannelId());
		noteService.deleteByExample(searchParams);
		String text = "All your notes from " + getName(command, args) + " have been removed";
		return new SlashCommandResult(text);
	}

	private String getName(SlashCommand command, MessageArguments args) {
		return args.hasTitle() ? "[" + args.getTitle() + "]" : command.getChannelName();
	}

	@Override
	protected Action getSupportedAction() {
		return Action.CLEAR;
	}
}
