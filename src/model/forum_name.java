package model;

import java.sql.Timestamp;

public class forum_name {
	private int id;
	private String name;
	private Timestamp start_date;
	private Timestamp last_post;
	private int no_of_messages;
	private int no_of_views;
	private int no_of_threads;
	private int no_of_posts;	
	
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
	public Timestamp getStart_date() {
		return start_date;
	}
	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}
	public Timestamp getLast_post() {
		return last_post;
	}
	public void setLast_post(Timestamp last_post) {
		this.last_post = last_post;
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
	public int getNo_of_threads() {
		return no_of_threads;
	}
	public void setNo_of_threads(int no_of_threads) {
		this.no_of_threads = no_of_threads;
	}
	public int getNo_of_posts() {
		return no_of_posts;
	}
	public void setNo_of_posts(int no_of_posts) {
		this.no_of_posts = no_of_posts;
	}			
}
