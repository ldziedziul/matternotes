package pl.dziedziul.matternotes.command.handler;

import org.springframework.stereotype.Component;

import pl.dziedziul.matternotes.command.Action;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

@Component
public class AboutCommandHandler extends ActionBasedCommandHandler {

	@Override
	public SlashCommandResult handle(SlashCommand command) {
		String text = "Follow me on twitter ;) https://twitter.com/ldziedziul\n" +
			"Checkout matternotes git repository: https://github.com/ldziedziul/matternotes";
		return new SlashCommandResult(text);
	}

	@Override
	protected Action getSupportedAction() {
		return Action.ABOUT;
	}
}
