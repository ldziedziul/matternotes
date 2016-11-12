package pl.dziedziul.matternotes.webhook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandProcessor {
	private static final Logger log = LoggerFactory.getLogger(CommandProcessor.class);

	private final CommandActionExtractor commandActionExtractor;

	public CommandProcessor(CommandActionExtractor commandActionExtractor) {
		this.commandActionExtractor = commandActionExtractor;
	}

	public SlashCommandResult process(SlashCommand command) {
		Action action = commandActionExtractor.extractAction(command);
		log.debug("Processing action {} with {}", action, command);
		SlashCommandResult result = new SlashCommandResult();
		result.setText("Dummy response *text* :+1: with action " + action);
		return result;
	}
}
