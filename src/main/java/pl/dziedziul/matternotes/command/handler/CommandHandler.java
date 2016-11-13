package pl.dziedziul.matternotes.command.handler;

import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

public interface CommandHandler {
	boolean isSupporting(SlashCommand command);

	SlashCommandResult handle(SlashCommand command);
}
