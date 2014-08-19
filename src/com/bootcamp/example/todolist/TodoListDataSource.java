package com.bootcamp.example.todolist;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.pm.LabeledIntent;

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
	
	/*
	 * Add new label. Label shouldn't be already present.
	 */
	public void addLabel(Label l);
	
	/*
	 * Update attributes of a label.
	 */
	public void updateLabel(Label l);
	
	/*
	 * Delete label.
	 */
	public void deleteLabel(Label l);
	
	/*
	 * Returns all the known labels.
	 */
	public List<Label> readLabels();
}
