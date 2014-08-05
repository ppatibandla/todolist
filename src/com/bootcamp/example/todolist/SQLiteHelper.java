/**
 * 
 */
package com.bootcamp.example.todolist;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author ppatibandla
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "todolist.db";
	public static final String TO_DO_LIST_TABLE = "todolist";
	public static final String COL_ITEM = "item";
	public static final String COL_ID = "_id";
	
	private static final String CREATE_DB = "create table " + TO_DO_LIST_TABLE
				+ "(" + COL_ID + " integer primary key autoincrement, "
				+ COL_ITEM + " text not null);";
	
	public SQLiteHelper(Context con){
		super(con, DB_NAME, null, DB_VERSION);
	}
	
	/* (non-Javadoc)
	 * Create tables needed for TodoList app.
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}