package pl.dziedziul.matternotes.command.handler;

import pl.dziedziul.matternotes.domain.Note;
import pl.dziedziul.matternotes.domain.NoteType;

public class NoteSearchParams {
	private String title;
	private String userId;
	private NoteType type;
	private String channelId;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setType(NoteType type) {
		this.type = type;
	}

	public NoteType getType() {
		return type;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelId() {
		return channelId;
	}

	public static NoteSearchParams of(Note note) {
		NoteSearchParams searchParams = new NoteSearchParams();
		searchParams.setTitle(note.getTitle());
		searchParams.setUserId(note.getUserId());
		searchParams.setType(note.getType());
		searchParams.setChannelId(note.getChannelId());
		return searchParams;
	}
}
