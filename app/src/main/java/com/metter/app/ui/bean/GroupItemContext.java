package com.metter.app.ui.bean;

public class GroupItemContext {
	int  id ;
	String text;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public GroupItemContext(){}
	public GroupItemContext(int id, String text) {
		this.id = id;
		this.text = text;
	}

}
