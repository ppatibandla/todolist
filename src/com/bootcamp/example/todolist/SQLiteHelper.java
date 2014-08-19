/**
 * 
 */
package com.bootcamp.example.todolist;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author ppatibandla
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "todolist.db";
	public static final String TO_DO_LIST_TABLE = "todolist";
	public static final String LABEL_TABLE = "label";
	public static final String COL_ITEM = "item";
	public static final String COL_ID = "_id";
	public static final String COL_DUE_DATE = "due_date";
	public static final String COL_LABEL = "label";
	
	public static final String COL_LABEL_COLOR = "label_color";
	private static final String CREATE_TO_DO_TABLE = "create table " + TO_DO_LIST_TABLE
				+ "(" + COL_ID + " integer primary key autoincrement, "
				+ COL_ITEM + " text not null, "
				+ COL_DUE_DATE + " text not null, "
				+ COL_LABEL + " text not null);";
	private static final String CREATE_LABEL_TABLE = "create table " + LABEL_TABLE
			+ "(" + COL_LABEL + " text not null primary key, "
			+ COL_LABEL_COLOR + " integer);";

			public SQLiteHelper(Context con){
		super(con, DB_NAME, null, DB_VERSION);
	}
	
	/* (non-Javadoc)
	 * Create tables needed for TodoList app.
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// First clear existing tables.
		String DELETE_TO_DO_TABLE = "Drop table if exists " + TO_DO_LIST_TABLE;
		String DELETE_LABEL_TABLE = "Drop table if exists " + LABEL_TABLE;
		db.execSQL(DELETE_TO_DO_TABLE);
		db.execSQL(DELETE_LABEL_TABLE);
		db.execSQL(CREATE_TO_DO_TABLE);
		db.execSQL(CREATE_LABEL_TABLE);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
/*		if (oldVersion < 3) {
			String alterCmd = "ALTER TABLE " + TO_DO_LIST_TABLE + " add COLUMN " + COL_DUE_DATE + " text";
			db.execSQL(alterCmd);
			Log.i("onUpgrade", " Added column due_date");
		}
		if (oldVersion < DB_VERSION) {
			String alterCmd = "ALTER TABLE " + TO_DO_LIST_TABLE + " add COLUMN " + COL_LABEL + " text";
			db.execSQL(alterCmd);
			Log.i("onUpgrade", " Added column label");
		}
*/
	}

}
