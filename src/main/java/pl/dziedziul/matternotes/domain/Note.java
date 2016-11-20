package pl.dziedziul.matternotes.domain;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class Note extends AbstractEntity {

	private static final Logger log = LoggerFactory.getLogger(Note.class);

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(nullable = false)
	private NoteType type;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "note")
	@Size(min = 1)
	private List<Message> messages = new ArrayList<>();

	@CreatedDate
	private ZonedDateTime dateCreated;

	@LastModifiedDate
	private ZonedDateTime lastUpdated;

	@Column(nullable = false)
	@NotNull
	private String username;

	@Column(nullable = false)
	@NotNull
	private String userId;

	@Column(nullable = false)
	@NotNull
	private String title;

	@Column(nullable = false)
	@NotNull
	private String channelName;

	@Column(nullable = false)
	@NotNull
	private String channelId;

	public NoteType getType() {
		return type;
	}

	public void setType(NoteType type) {
		this.type = type;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ZonedDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(ZonedDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getText() {
		return messages.stream()
			.map(Message::getText)
			.collect(Collectors.joining(System.lineSeparator()));
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void addMessage(String text) {
		Message message = new Message(text);
		messages.add(message);
		message.setNote(this);
	}

	public void deleteLastMessage() {
		if (messages.isEmpty()) {
			log.warn("Trying to remove message from empty note");
		} else {
			messages.remove(messages.size() - 1);
		}
	}

	public Message getLastMessage() {
		return messages.get(messages.size() - 1);
	}
}
