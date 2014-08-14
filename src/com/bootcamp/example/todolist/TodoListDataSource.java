package com.bootcamp.example.todolist;

import java.io.IOException;
import java.util.List;

import android.content.Context;

public interface TodoListDataSource {
	public void open();
	public void close();

	public void addItem(TodoItem item);
	public void updateItem(TodoItem oldval, TodoItem newval);
	public void deleteItem(TodoItem item);
	
	/*
	 * Returns items in the order of addition.
	 */
	public List<TodoItem> readItems();

}
