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
		{SQLiteHelper.COL_ID, SQLiteHelper.COL_ITEM};
	
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
	public void addItem(String item) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COL_ITEM, item);
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
	public void updateItem(String oldval, String newitem) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COL_ITEM, newitem);
		db.update(SQLiteHelper.TO_DO_LIST_TABLE, values,
				  SQLiteHelper.COL_ITEM + " = " + "\"" + oldval + "\"", null);
		
	}

	@Override
	public void deleteItem(String item) {
		db.delete(SQLiteHelper.TO_DO_LIST_TABLE,
				  SQLiteHelper.COL_ITEM + " = " + "\"" + item + "\"", null);
	}

	@Override
	public List<String> readItems() {
		List<String> items = new ArrayList<String>();
		Cursor cursor = db.query(SQLiteHelper.TO_DO_LIST_TABLE,
								 allColumns, null,
								 null, null, null, SQLiteHelper.COL_ID);
		Log.d("readItems", String.valueOf(cursor.getCount()));
		cursor.moveToFirst();
		while (! cursor.isAfterLast()) {
			items.add(cursor.getString(1));
			cursor.moveToNext();
		}
		return items;
	}

}
