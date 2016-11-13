package pl.dziedziul.matternotes.command.handler;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.webhook.SlashCommand;

public abstract class ActionBasedCommandHandler implements CommandHandler {
	@Override
	public boolean isSupporting(SlashCommand command) {
		return command.getAction().equals(getSupportedAction());
	}

	protected abstract Action getSupportedAction();

}
