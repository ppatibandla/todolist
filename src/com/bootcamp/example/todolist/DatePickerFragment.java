package com.bootcamp.example.todolist;

import java.security.PublicKey;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
								implements DatePickerDialog.OnDateSetListener {
	private DatePickerFragmentListner lisner;
	private Calendar mDate;
	
	public interface DatePickerFragmentListner {
		public void DateSet(Calendar c);
	}
	
	DatePickerFragment(Calendar c) {
		mDate = c;
	}
	
	@Override
	public void onAttach(Activity a){
		super.onAttach(a);
		try {
			lisner = (DatePickerFragmentListner)a;
		} catch (ClassCastException e) {
			throw new ClassCastException(a.toString()
						+ "must implement DatePickerFragmentListner");
		}
	}

	
	@Override
	public Dialog onCreateDialog(Bundle s){
		return new DatePickerDialog(getActivity(), this,
									mDate.get(Calendar.YEAR),
									mDate.get(Calendar.MONTH),
									mDate.get(Calendar.DATE));
	}
	public void onDateSet(DatePicker v, int y, int m, int d) {
		mDate.set(y, m, d);
		lisner.DateSet(mDate);
	}

}
