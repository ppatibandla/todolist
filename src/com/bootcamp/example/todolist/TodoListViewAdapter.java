package com.bootcamp.example.todolist;

import java.util.ArrayList;

import android.content.Context;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TodoListViewAdapter extends BaseAdapter {
	private static ArrayList<TodoItem> todoItemsList;
	private LayoutInflater mInflater;
	
	static class ViewHolder {
		TextView todoString;
		TextView dueDate;
	};
	
	public TodoListViewAdapter(Context c, ArrayList<TodoItem> l) {
		todoItemsList = l;
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
		
		holder.todoString.setText(todoItemsList.get(position).todo());
		holder.dueDate.setText(todoItemsList.get(position).dueDateStr());
		return convertView;
	}

}
