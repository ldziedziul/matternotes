package pl.dziedziul.matternotes.command.handler;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pl.dziedziul.matternotes.command.Action;

@Component
public class MessageArgumentParser {
	public MessageArguments parse(String textToParse) {
		if (textToParse == null) {
			return new MessageArguments("", "");
		}
		String cleanedTextToParse = cleanText(textToParse);
		String title = "";
		String text = "";
		Pattern pattern = Pattern.compile("(\\[(.*)\\])?\\s?(.*)");
		Matcher matcher = pattern.matcher(cleanedTextToParse);
		if (matcher.find()) {
			title = Optional.ofNullable(matcher.group(2)).orElse("");
			text = Optional.ofNullable(matcher.group(3)).orElse("");
		}
		return new MessageArguments(title, text);
	}

	private String cleanText(String textToParse) {
		Assert.notNull("Text to parse should be provided");
		String cleanedTextToParse = textToParse;
		String[] tokens = StringUtils.split(textToParse);
		if (tokens.length > 0) {
			String firstToken = tokens[0];
			Optional<String> actionLiteral = Arrays.stream(Action.values())
				.filter(a -> !a.equals(Action.NEW))
				.map(Enum::name)
				.map(String::toLowerCase)
				.filter(firstToken::equals).findFirst();
			if (actionLiteral.isPresent()) {
				cleanedTextToParse = textToParse.substring(actionLiteral.get().length());
			}
		}
		return cleanedTextToParse.trim();
	}
}
