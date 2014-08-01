package com.bootcamp.example.todolist;

import junit.framework.Assert;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {
	public final static String EXTRA_EDITED_ITEM = "com.bootcamp.example.todolist.EDITEDITEM";
	private int pos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		Intent intent = getIntent();
		String item = intent.getStringExtra(MainActivity.EXTRA_ITEM);
		pos = intent.getIntExtra(MainActivity.EXTRA_POS, -1);
		
		Assert.assertFalse(pos == -1);
		
		EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
		etEditItem.setText(item);
	}

	public void onbtSaveClick(View v){
		String item = ((EditText) findViewById(R.id.etEditItem)).getText().toString();
		Log.i("On btSave Click", item);
		Intent data = new Intent();
		data.putExtra(MainActivity.EXTRA_ITEM, item);
		data.putExtra(MainActivity.EXTRA_POS, pos);
		setResult(RESULT_OK, data);
		finish();
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
