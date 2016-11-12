package pl.dziedziul.matternotes.webhook;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlashCommandResult {
	@JsonProperty("response_type")
	private ResponseType responseType = ResponseType.EPHEMERAL;
	private String text;
	@JsonProperty("goto_location")
	private String gotoLocation;

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGotoLocation() {
		return gotoLocation;
	}

	public void setGotoLocation(String gotoLocation) {
		this.gotoLocation = gotoLocation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof SlashCommandResult)) return false;

		SlashCommandResult that = (SlashCommandResult) o;

		return new EqualsBuilder()
			.append(responseType, that.responseType)
			.append(text, that.text)
			.append(gotoLocation, that.gotoLocation)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(responseType)
			.append(text)
			.append(gotoLocation)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("responseType", responseType)
			.append("text", text)
			.append("gotoLocation", gotoLocation)
			.toString();
	}


	public static final class Builder {
		private ResponseType responseType = ResponseType.IN_CHANNEL;
		private String text;
		private String gotoLocation;

		private Builder() {
		}

		public static Builder newSlashCommandResult() {
			return new Builder();
		}

		public Builder responseType(ResponseType responseType) {
			this.responseType = responseType;
			return this;
		}

		public Builder text(String text) {
			this.text = text;
			return this;
		}

		public Builder gotoLocation(String gotoLocation) {
			this.gotoLocation = gotoLocation;
			return this;
		}

		public SlashCommandResult build() {
			SlashCommandResult slashCommandResult = new SlashCommandResult();
			slashCommandResult.setResponseType(responseType);
			slashCommandResult.setText(text);
			slashCommandResult.setGotoLocation(gotoLocation);
			return slashCommandResult;
		}
	}
}
