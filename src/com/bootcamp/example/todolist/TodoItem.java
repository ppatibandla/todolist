package com.bootcamp.example.todolist;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;

public class TodoItem implements Serializable {
	private int id;
	private String todoString;
	private String dueDate = "No Due Date";
	private String label = "Misc";
	public static java.text.DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	TodoItem(String todo) {
		todoString = todo;
		id = Integer.MAX_VALUE;
	}

	public TodoItem(String todo, Calendar due) {
		todoString = todo;
		dueDate = dateFormat.format(due);
		id = Integer.MAX_VALUE;
	}

	public TodoItem(String todo, String due) {
		todoString = todo;
		dueDate = due;
		id = Integer.MAX_VALUE;
	}

	public TodoItem(String todo, String due, String l) {
		todoString = todo;
		dueDate = due;
		id = Integer.MAX_VALUE;
		label = l;
	}

	public int getid() {
		return id;
	}

	public String todo() {
		return todoString;
	}

	
	public String dueDateStr() {
		return dueDate;
	}

	public void setTodo(String todo) {
		todoString = todo;
	}

	public void setDueDate(Calendar d) {
		dueDate = dateFormat.format(d.getTime());
	}

	public String toString() {
		return dueDate + "\t : " + todoString;
	}

	public void setLabel(String l){
		label = l;
	}

	public String getLabel() {
		return label;
	}
}
