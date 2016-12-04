package pl.dziedziul.matternotes.command.handler;

import org.springframework.stereotype.Component;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

@Component
public class HelpCommandHandler extends ActionBasedCommandHandler {
	@Override
	protected Action getSupportedAction() {
		return Action.HELP;
	}

	@Override
	public SlashCommandResult handle(SlashCommand command) {
		String message = "Command | Description\n" +
			"------ | ------\n" +
			"/notes my private note | Creates 'my private note' as your note for the current channel/conversation. If one already exists, it will append it to your note\n" +
			"/notes [title] my private note | Creates 'my private note' under a 'title' notepad. If 'title' already exists, it will append it to your note\n" +
			"/notes show | Shows your notes for the current channel/conversation\n" +
			"/notes show [title] | Shows your notes with the specified title. Square brackets are necessary and not part of the name\n" +
			"/notes show all | Show all your notes, grouped by channel/conversation\n" +
			"/notes clear | Clears permanently all your notes for the current channel/conversation.\n" +
			"/notes clear [title] | Clears permanently all your notes with the specified title.\n" +
			"/notes undo | Undo your latest change for the note for the current channel, i.e. your latest appended message will be deleted\n" +
			"/notes undo [title] | Undo your latest change for the note with the specified title, i.e. your latest appended message will be deleted\n" +
			"/notes help | Displays this list of commands\n" +
			"/notes about | Shows some information about Matternotes";
		return new SlashCommandResult(message);
	}
}
