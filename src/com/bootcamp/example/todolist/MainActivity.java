package com.bootcamp.example.todolist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.Assert;

import android.R.integer;
import android.R.string;
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
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;

/*
 * Simple Android app to track to do items. This app support add, remove and edit
 * functionalities. TodoList App saves to do items to file to persist them.
 */
public class MainActivity extends Activity {
	public final static String EXTRA_ITEM = "com.bootcamp.example.todolist.EDITITEM";
	public final static String EXTRA_POS = "com.bootcamp.example.todolist.POS";

	public final static int EDIT_ITEM_REQ = 1;
	
	private final String TODO_FILE = "todolist.txt";
	private ArrayList<TodoItem> items;
	
	private TodoListViewAdapter itemsAdapter;
	private ListView lvItems;
	private Map<String, Label> labelMap;
	private TodoListDataSource dataSource;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        dataSource = new TodoListDB(this);
        dataSource.open();
		items = new ArrayList<TodoItem>(dataSource.readItems());
		List<Label> labels = dataSource.readLabels();
		
		labelMap = new HashMap<String, Label>();
		for (Label l : labels) {
			labelMap.put(l.getLabel(), l);
		}
		
        itemsAdapter = new TodoListViewAdapter(this, items, labelMap);
        
        lvItems.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();
        setupListViewListners();
    }
    
    /*
     * Register ItemClick and ItemLockClick listeners.
     * ItemClick : Launch EditAction to edit item.
     * ItemLongClick : Remove item.
     */
    private void setupListViewListners(){
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id ) {
    			dataSource.deleteItem(items.get(pos));
    			items.remove(pos);
    			itemsAdapter.notifyDataSetChanged();
    			return true;
    		}
    	});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
    			Log.d("OnClick", items.get(pos).todo());
    			editItem(pos);
    		}
    	});
    }
    
    private void editItem(int pos) {
		Intent intent = new Intent(MainActivity.this, EditActivity.class);
		intent.putExtra(EXTRA_ITEM, items.get(pos));
		intent.putExtra(EXTRA_POS, pos);
		startActivityForResult(intent, EDIT_ITEM_REQ);
    }
    
    /*
     * Add item to in-memory and on-disk items list.
     * Refresh ListView to show new item.
     */
    public void onbtAddClick(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	addItem(new TodoItem(etNewItem.getText().toString()));
    	etNewItem.setText("");
//    	editItem(items.size() - 1);
    	showToast("Item Added");
    }
    
    private void showToast(String msg){
    	Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
    /*
     * Process the result of Edit Activity. Response should contain
     * EXTRA_ITEM : Item (String) after edit
     * EXTRA_POS : position of the item in items, passed in startActivity.
     * 
     * Updates item in-memory and on-disk. Refreshes ListView.
     */
    protected void onActivityResult(int request, int result, Intent data){
    	if (request == EDIT_ITEM_REQ && result == RESULT_OK) {
    		// Log.d("EditResult", data.getStringExtra(EXTRA_ITEM));
    		updateItem((TodoItem)data.getSerializableExtra(EXTRA_ITEM), data.getIntExtra(EXTRA_POS, -1));
    		showToast("Item Edited");
    	}
    }
    
    private void updateItem(TodoItem item, int pos){
    	Assert.assertTrue(pos != -1 && pos < items.size());
    	updateItems(item, pos, false);
    }
    
    private void addItem(TodoItem  item) {
    	updateItems(item, items.size(), true);
    }
    
    /*
     * Updates(/ adds) item in-memory and on-disk items list.
     * Refreshes ListView.
     */
    private void updateItems(TodoItem  item, int pos, Boolean add) {
    	if (add) {
    		if (labelMap.get(item.getLabel()) == null) {
    			addLabel(item.getLabel());
    		}
    		items.add(pos, item);
    		dataSource.addItem(item);
    	} else {
    		if (labelMap.get(item.getLabel()) == null) {
    			addLabel(item.getLabel());
    		}
    		TodoItem oldval = items.get(pos); 
    		items.set(pos, item);
    		dataSource.updateItem(oldval, item);
    	}
    	itemsAdapter.notifyDataSetChanged();
    }
    
    private void addLabel(String l){
    	int temp = (int)(Math.random() * 0xFFFFFF);
    	int color = Color.rgb((temp >> 16)&0xFF,
    						  (temp >> 8)&0xFF,
    						  temp & 0xFF);
		Label label =
				new Label(l, Integer.toHexString(color));
		dataSource.addLabel(label);
		labelMap.put(l, label);
    }
    
    /*
     * Reads content from reader to List<String>, where each string would
     * represent one line.
     */
/*
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
*/
    /*
     * Read to do items from on-disk to do list file into 'items' ArrayList.
     * To do items should be separated by new line.
     * 
     */
/*
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, TODO_FILE);
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
*/    
    /*
     * Save to do items in 'items' ArrayList to TODO_FILE. On the disk
     * to do items will be separated by new line.
     */
/*
    private void saveItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, TODO_FILE);
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
 */  
}
