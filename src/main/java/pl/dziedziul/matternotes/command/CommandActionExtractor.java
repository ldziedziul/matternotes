package pl.dziedziul.matternotes.command;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CommandActionExtractor {

	private static final Action DEFAULT_ACTION = Action.HELP;

	public Action extractAction(String text) {
		Assert.notNull(text, "Text in command should be provided");
		String[] tokens = StringUtils.split(text);
		if (tokens.length > 0) {
			return findActionByName(tokens[0]);
		} else {
			return DEFAULT_ACTION;
		}
	}

	private Action findActionByName(String stringAction) {
		return Arrays.stream(Action.values())
			.filter(a -> nameLowerCase(a).equals(stringAction))
			.findFirst()
			.orElse(Action.NEW);
	}

	private String nameLowerCase(Action a) {
		return a.name().toLowerCase();
	}
}
