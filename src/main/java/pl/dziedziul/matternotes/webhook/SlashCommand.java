package pl.dziedziul.matternotes.webhook;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SlashCommand implements Serializable {
	private String channelId;
	private String channelName;
	private String command;
	private String responseUrl;
	private String teamDomain;
	private String teamId;
	private String text;
	private String token;
	private String userId;
	private String userName;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setChannel_id(String channelId) {
		setChannelId(channelId);
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setChannel_name(String channelName) {
		setChannelName(channelName);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getResponseUrl() {
		return responseUrl;
	}

	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}

	public void setResponse_url(String responseUrl) {
		setResponseUrl(responseUrl);
	}

	public String getTeamDomain() {
		return teamDomain;
	}

	public void setTeamDomain(String teamDomain) {
		this.teamDomain = teamDomain;
	}

	public void setTeam_domain(String teamDomain) {
		setTeamDomain(teamDomain);
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public void setTeam_id(String teamId) {
		setTeamId(teamId);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUser_id(String userId) {
		setUserId(userId);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUser_name(String userName) {
		setUserName(userName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof SlashCommand)) return false;

		SlashCommand command1 = (SlashCommand) o;

		return new EqualsBuilder()
			.append(channelId, command1.channelId)
			.append(channelName, command1.channelName)
			.append(command, command1.command)
			.append(responseUrl, command1.responseUrl)
			.append(teamDomain, command1.teamDomain)
			.append(teamId, command1.teamId)
			.append(text, command1.text)
			.append(token, command1.token)
			.append(userId, command1.userId)
			.append(userName, command1.userName)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(channelId)
			.append(channelName)
			.append(command)
			.append(responseUrl)
			.append(teamDomain)
			.append(teamId)
			.append(text)
			.append(token)
			.append(userId)
			.append(userName)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("channelId", channelId)
			.append("channelName", channelName)
			.append("command", command)
			.append("responseUrl", responseUrl)
			.append("teamDomain", teamDomain)
			.append("teamId", teamId)
			.append("text", text)
			.append("token", token)
			.append("userId", userId)
			.append("userName", userName)
			.toString();
	}
}