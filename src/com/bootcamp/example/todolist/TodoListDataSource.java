package com.bootcamp.example.todolist;

import java.io.IOException;
import java.util.List;

import android.content.Context;

public interface TodoListDataSource {
	public void open();
	public void close();

	public void addItem(String item);
	public void updateItem(String oldval, String newval);
	public void deleteItem(String item);
	
	/*
	 * Returns items in the order of addition.
	 */
	public List<String> readItems();

}
