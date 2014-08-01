package com.bootcamp.example.todolist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;


public class MainActivity extends Activity {

	public final static String EXTRA_ITEM = "com.bootcamp.example.todolist.EDITITEM";
	public final static String EXTRA_POS = "com.bootcamp.example.todolist.POS";
	public final static int EDIT_ITEM_REQ = 1;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        itemsAdapter.notifyDataSetChanged();
        setupListViewListner();
    }
    
    private void setupListViewListner(){
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id ) {
    			items.remove(pos);
    			saveItems();
    			itemsAdapter.notifyDataSetChanged();
    			return true;
    		}
    	});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
    			Log.d("OnClick", items.get(pos));
    			Intent intent = new Intent(MainActivity.this, EditActivity.class);
    			intent.putExtra(EXTRA_ITEM, items.get(pos));
    			intent.putExtra(EXTRA_POS, pos);
    			startActivityForResult(intent, EDIT_ITEM_REQ);
//    			startActivity(intent);
    		}
    	});
    }
    
    protected void onActivityResult(int request, int result, Intent data){
    	if (request == EDIT_ITEM_REQ && result == RESULT_OK) {
    		Log.d("EditResult", data.getStringExtra(EXTRA_ITEM));
    		updateItem(data.getStringExtra(EXTRA_ITEM), data.getIntExtra(EXTRA_POS, -1));
    	}
    }
    private void updateItem(String item, int pos){
    	Assert.assertTrue(pos != -1 && pos < items.size());
    	updateItems(item, pos, false);
    }
    
    private void addItem(String item) {
    	updateItems(item, items.size(), true);
    }
    
    private void updateItems(String item, int pos, Boolean add) {
    	if (add) {
    		items.add(pos, item);
    	} else {
    		items.set(pos, item);
    	}
    	itemsAdapter.notifyDataSetChanged();
    	saveItems();
    }
    
    public void onbtAddClick(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	addItem(etNewItem.getText().toString());
    	etNewItem.setText("");
    }
    
    private List<String> readLines(Reader in) {
    	BufferedReader reader = new BufferedReader(in);
    	List<String> list = new ArrayList<String>();
    	try {
    		String line = reader.readLine();
    		while (line != null) {
    			list.add(line);
    			line = reader.readLine();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todolist.txt");
    	Log.d("readItems", "In readItems");
    	try {
    		items = new ArrayList<String>(readLines(new FileReader(todoFile)));
    		Log.d("readItems", String.valueOf(items.size()));
    		for (String temp : items){
    			Log.d("readItems", temp);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    private void saveItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todolist.txt");
    	Log.d("saveItems", "In saveItems");
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(todoFile));
    		for (String item : items){
    			Log.d("saveItems", item);
    			writer.write(item);
    			writer.newLine();
    		}
    		writer.close();
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
}
