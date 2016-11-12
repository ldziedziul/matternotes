package pl.dziedziul.matternotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.dziedziul.matternotes.webhook.CommandProcessor;
import pl.dziedziul.matternotes.webhook.SlashCommand;
import pl.dziedziul.matternotes.webhook.SlashCommandResult;

@RestController
@RequestMapping("${matternotes.entry.point.uri:/notes}")
public class NotesController {
	private static final Logger log = LoggerFactory.getLogger(NotesController.class);
	private final CommandProcessor commandProcessor;

	public NotesController(CommandProcessor commandProcessor) {
		this.commandProcessor = commandProcessor;
	}

	@RequestMapping(method = RequestMethod.POST)
	public SlashCommandResult process(@ModelAttribute SlashCommand command) {
		log.debug("Received command: {}", command);
		return commandProcessor.process(command);
	}
}
