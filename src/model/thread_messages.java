package model;

import java.sql.Timestamp;

public class thread_messages {

	private int id;
	private String message_content;
	private int posted_by;
	private Timestamp posting_date;
	private int thread_id;
	private int forum_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public int getPosted_by() {
		return posted_by;
	}
	public void setPosted_by(int posted_by) {
		this.posted_by = posted_by;
	}
	public Timestamp getPosting_date() {
		return posting_date;
	}
	public void setPosting_date(Timestamp posting_date) {
		this.posting_date = posting_date;
	}
	public int getThread_id() {
		return thread_id;
	}
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}		
}
