package com.bootcamp.example.todolist;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.ClipData.Item;
import android.graphics.Color;
import android.text.StaticLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TodoListViewAdapter extends BaseAdapter {
	private static ArrayList<TodoItem> todoItemsList;
	private static Map<String, Label> labelMap;
	private LayoutInflater mInflater;
	
	static class ViewHolder {
		TextView todoString;
		TextView dueDate;
	};
	
	public TodoListViewAdapter(Context c, ArrayList<TodoItem> iList,
							   Map<String, Label> lMap) {
		todoItemsList = iList;
		labelMap = lMap;
		mInflater = LayoutInflater.from(c);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return todoItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return todoItemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private int getBGColor(TodoItem i) {
		Log.d("getBGColor", "#"+labelMap.get(i.getLabel()).getColor());
		return Color.parseColor("#"+labelMap.get(i.getLabel()).getColor());
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.custom_todo_item_row, null);
			holder = new ViewHolder();
			holder.todoString = (TextView) convertView.findViewById(R.id.tvLVTodoString);
			holder.dueDate = (TextView) convertView.findViewById(R.id.tvLVDueDate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TodoItem item = todoItemsList.get(position);
		Log.d("TodoListViewAdapter", item.todo() + " " + item.dueDateStr() + " ," + item.getLabel()+";");

		holder.todoString.setText(item.todo());
		holder.dueDate.setText(item.dueDateStr());
		convertView.setBackgroundColor(getBGColor(item));

		/*
		if (item.getLabel().equals("High")) {
			Log.d("TodoListViewAdapter", "label is High");
			convertView.setBackgroundColor(Color.parseColor("#63D958"));
		} else if (item.getLabel().equals("Medium")) {
			Log.d("TodoListViewAdapter", "label is Medium");
			convertView.setBackgroundColor(Color.parseColor("#58D9D0"));
		} else if (item.getLabel().equals("Low")) {
			Log.d("TodoListViewAdapter", "label is Low");
			convertView.setBackgroundColor(Color.parseColor("#BDCDDB"));
		}
		*/
		return convertView;
	}

}
