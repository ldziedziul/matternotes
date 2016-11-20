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
			"/notes my private note | Records 'my private note' as your note for the current channel/conversation. If one already exists, it will append it to your note\n" +
			"/notes [title] my private note | Records 'my private note' under a 'title' notepad. If 'title' already exists, it will append it to your note\n" +
			"/notes show | Shows your notes for the current channel/conversation\n" +
			"/notes show [title] | Shows your notes for the specified title. Square brackets are necessary and not part of the name\n" +
			"/notes show all | Show all your notes, grouped by channel/conversation\n" +
			"/notes clear | Clears all your notes for the current channel. They can't be retrieved, so make sure you were done with them\n" +
			"/notes clear [title] | Clears all your notes saved under 'title'. They can't be retrieved, so make sure you were done with them\n" +
			"/notes undo | Removes your latest note for the current channel. Your latest appended message will be deleted\n" +
			"/notes undo [title] | Removes your latest note for the 'title' notepad. Your latest appended message will be deleted\n" +
			"/notes help | Displays this (hopefully) helpful list of commands\n" +
			"/notes feedback | Shows link to matternotes github repository";
		return new SlashCommandResult(message);
	}
}
