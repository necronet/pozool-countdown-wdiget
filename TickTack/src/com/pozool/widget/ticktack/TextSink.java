package com.pozool.widget.ticktack;

import java.util.Locale;

import android.content.Context;
import android.content.ContextWrapper;

public class TextSink extends ContextWrapper{
	
	public static TextSink init(Context context) {
		return new TextSink(context);
	}
	
	private TextSink(Context context) {
		super(context);
	}
	
	
	/**
	 * Will retrieve wich String is more suitable for the days number. The following cases are:
	 * 
	 * If days are less than 0: "Days since" is returned
	 * If days are greater than 0: "Days left" is returned
	 * If days is 0: "Today" is returned
	 * 
	 * */
	public String parse(int numberOfDays) {
		if ( numberOfDays > 0 ) {
			return getString(R.string.days_left_text).toUpperCase(Locale.getDefault());
		} else if (numberOfDays < 0) {
			return getString(R.string.days_since_text).toUpperCase(Locale.getDefault());
		}
		else{
			return getString(R.string.today_text).toUpperCase(Locale.getDefault());
		}
	}
	
}
