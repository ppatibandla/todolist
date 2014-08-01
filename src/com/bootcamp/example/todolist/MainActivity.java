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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
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
    }
    
    public void onbtAddClick(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	items.add(etNewItem.getText().toString());
    	itemsAdapter.notifyDataSetChanged();
    	etNewItem.setText("");
    	saveItems();
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
    	Log.i("readItems", "In readItems");
    	try {
    		items = new ArrayList<String>(readLines(new FileReader(todoFile)));
    		Log.i("readItems", String.valueOf(items.size()));
    		for (String temp : items){
    			Log.i("readItems", temp);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    private void saveItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todolist.txt");
    	Log.i("saveItems", "In saveItems");
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(todoFile));
    		for (String item : items){
    			writer.write(item);
    			writer.newLine();
    		}
    		writer.close();
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
}
