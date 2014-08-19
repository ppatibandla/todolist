package com.bootcamp.example.todolist;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TodoListDB implements TodoListDataSource {
	private SQLiteHelper dbHelper;
	private SQLiteDatabase db;
	private String[] allColumns =
		{SQLiteHelper.COL_ID, SQLiteHelper.COL_ITEM, SQLiteHelper.COL_DUE_DATE, SQLiteHelper.COL_LABEL};
	
	public TodoListDB(Context c) {
		dbHelper = new SQLiteHelper(c);
	}
	
	@Override
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	@Override
	public void close() {
		dbHelper.close();
	}
	
	@Override
	public void addItem(TodoItem item) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COL_ITEM, item.todo());
		values.put(SQLiteHelper.COL_DUE_DATE, item.dueDateStr());
		values.put(SQLiteHelper.COL_LABEL, item.getLabel());
		db.insert(SQLiteHelper.TO_DO_LIST_TABLE, null, values);
	}

	/*
	 * (non-Javadoc)
	 * @todo Updating and deleting item based on string value is very
	 * inefficient. Change this to use primary key.
	 * 
	 * @see com.bootcamp.example.todolist.TodoListDataSource#updateItem(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateItem(TodoItem oldval, TodoItem newitem) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COL_ITEM, newitem.todo());
		values.put(SQLiteHelper.COL_DUE_DATE, newitem.dueDateStr());
		values.put(SQLiteHelper.COL_LABEL, newitem.getLabel());
		db.update(SQLiteHelper.TO_DO_LIST_TABLE, values,
				  SQLiteHelper.COL_ITEM + " = " + "\"" + oldval.todo() + "\"", null);
		
	}

	@Override
	public void deleteItem(TodoItem item) {
		db.delete(SQLiteHelper.TO_DO_LIST_TABLE,
				  SQLiteHelper.COL_ITEM + " = " + "\"" + item.todo() + "\"", null);
	}

	@Override
	public List<TodoItem> readItems() {
		List<TodoItem> items = new ArrayList<TodoItem>();
		Log.i("readItems", "Trying to read all items from table");
		Cursor cursor = db.query(SQLiteHelper.TO_DO_LIST_TABLE,
								 allColumns, null,
								 null, null, null, SQLiteHelper.COL_ID);
		Log.d("readItems", String.valueOf(cursor.getCount()));
		for (String i : cursor.getColumnNames()) {
			Log.d("readItems", i);
		}
		cursor.moveToFirst();
		while (! cursor.isAfterLast()) {
			items.add(new TodoItem(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
			cursor.moveToNext();
		}
		return items;
	}

}
