package pl.dziedziul.matternotes.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pl.dziedziul.matternotes.command.handler.CommandHandler;
import pl.dziedziul.matternotes.command.handler.FallbackCommandHandler;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

@Component
public class CommandProcessor {
	private static final Logger log = LoggerFactory.getLogger(CommandProcessor.class);

	private final List<CommandHandler> commandHandlers;
	private final CommandActionExtractor commandActionExtractor;
	private final FallbackCommandHandler fallbackCommandHandler;

	public CommandProcessor(CommandActionExtractor commandActionExtractor, List<CommandHandler> commandHandlers) {
		Assert.notNull(commandActionExtractor, "Action extractour should be provided");
		Assert.notNull(commandHandlers, "Command handler should be provided");
		this.commandActionExtractor = commandActionExtractor;
		this.commandHandlers = commandHandlers;
		fallbackCommandHandler = new FallbackCommandHandler();
	}

	public SlashCommandResult process(SlashCommand command) {
		Action action = commandActionExtractor.extractAction(command.getText());
		command.setAction(action);
		log.debug("Processing action {} with {}", action, command);
		return findHandler(command).handle(command);
	}

	private CommandHandler findHandler(SlashCommand command) {
		return commandHandlers.stream()
			.filter(h -> h.isSupporting(command))
			.findFirst()
			.orElse(fallbackCommandHandler);
	}
}
