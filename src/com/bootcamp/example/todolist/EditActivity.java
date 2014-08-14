package com.bootcamp.example.todolist;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.bootcamp.example.todolist.DatePickerFragment.DatePickerFragmentListner;

import junit.framework.Assert;
import android.R.string;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
 * Provide functionality to edit given string (to do item).
 * onCreate intent:
 * EXTRA_ITEM : string (to do item) that user wants to edit
 * EXTRA_POS  : integer to identify edited to do item on response.
 * EditActivity treats EXTRA_POS as an opaque integer and it doesn't use it.
 * 
 * Result:
 * EXTRA_ITEM : Edited string (item).
 * EXTRA_POS  : EXTRA_POS passed in onCreate intent.
 */
public class EditActivity extends Activity
						  implements DatePickerFragmentListner{

	private TodoItem item = null;
	int pos = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		Intent intent = getIntent();
		item = (TodoItem) intent.getSerializableExtra(MainActivity.EXTRA_ITEM);
		pos = intent.getIntExtra(MainActivity.EXTRA_POS, -1);
		
		EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
		etEditItem.setText(item.todo());
		
		
		Button btDueDate = (Button) findViewById(R.id.btDueDate);
		btDueDate.setText(item.dueDateStr());
	}

	public void onbtSaveClick(View v){
		String todo = ((EditText) findViewById(R.id.etEditItem)).getText().toString();
		item.setTodo(todo);
		Log.d("onbtSaveClick", item.todo());
		Intent data = new Intent();
		data.putExtra(MainActivity.EXTRA_ITEM, item);
		data.putExtra(MainActivity.EXTRA_POS, pos);
		setResult(RESULT_OK, data);
		finish();
	}
	
	public void onbtDueDateClick(View v){
		Log.i("onbtDueDateClick", "Clicked");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		
		Calendar d = null;
		try {
			d.setTime(TodoItem.dateFormat.parse(item.dueDateStr()));
		} catch (Exception e) {
			d = Calendar.getInstance();
		}
		DatePickerFragment dpf = new DatePickerFragment(d);
		dpf.show(ft, "Due Date");
	}
	
	public void DateSet(Calendar d) {
		item.setDueDate(d);
		Button btDueDate = (Button) findViewById(R.id.btDueDate);
		btDueDate.setText(TodoItem.dateFormat.format(d.getTime()));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
