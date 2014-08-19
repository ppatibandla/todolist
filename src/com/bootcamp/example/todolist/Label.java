package com.bootcamp.example.todolist;

public class Label {
	private String label;
	private String color;
	
	public Label(String l, String c) {
		label = l;
		color = c;
	}
	public String getLabel() {
		return label;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String c) {
		color = c;
	}
}
