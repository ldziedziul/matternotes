package pl.dziedziul.matternotes.command.handler;

import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public class FallbackCommandHandler implements CommandHandler {
	@Override
	public boolean isSupporting(SlashCommand command) {
		return true;
	}

	@Override
	public SlashCommandResult handle(SlashCommand command) {
		return new SlashCommandResult("Unknown command: " + command.getText());
	}
}
