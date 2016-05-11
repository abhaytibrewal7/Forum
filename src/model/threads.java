package model;

import java.sql.Timestamp;

public class threads {
	
	private int id;
	private String name;
	private int author;
	private int forum_id;
	private int no_of_messages;
	private int no_of_views;
	private Timestamp last_post;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}
	public int getNo_of_messages() {
		return no_of_messages;
	}
	public void setNo_of_messages(int no_of_messages) {
		this.no_of_messages = no_of_messages;
	}
	public int getNo_of_views() {
		return no_of_views;
	}
	public void setNo_of_views(int no_of_views) {
		this.no_of_views = no_of_views;
	}
	public Timestamp getLast_post() {
		return last_post;
	}
	public void setLast_post(Timestamp last_post) {
		this.last_post = last_post;
	}
	
	
	
}
