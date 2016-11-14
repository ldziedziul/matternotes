package pl.dziedziul.matternotes.command.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MessageArguments {
	private String text;
	private String title;

	public MessageArguments(String title, String text) {
		this.text = text;
		this.title = title;
	}

	public boolean hasTitle() {
		return StringUtils.isNotEmpty(title);
	}

	public boolean hasText() {
		return StringUtils.isNotEmpty(text);
	}

	public String getText() {
		return text;
	}


	public String getTitle() {
		return title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof MessageArguments)) return false;

		MessageArguments that = (MessageArguments) o;

		return new EqualsBuilder()
			.append(title, that.title)
			.append(text, that.text)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(title)
			.append(text)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("title", title)
			.append("text", text)
			.toString();
	}
}
