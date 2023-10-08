package com.veullustigpws.pws.connection;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1414023822821265008L;
	
	private String description;
	private Object content;
	
	public Message(String description, Object content) {
		this.description = description;
		this.content = content;
	}
	public Message(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public Object getContent() {
		return content;
	}
	
}
