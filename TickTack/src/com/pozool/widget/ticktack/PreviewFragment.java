package com.pozool.widget.ticktack;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PreviewFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.preview, container, false);

		return view;
	}

	public void updateDate(String days) {
		int dayNumber = Integer.parseInt(days);
		
		TextView textLeft = (TextView)getView().findViewById(R.id.text_left);
		TextView textDayLeft = (TextView) getView().findViewById(
				R.id.text_day_left);
		
		//TODO: we might use a class that hanlde all cases and return resoruces
		if ( dayNumber > 0 ) {
			textLeft.setText(getString(R.string.days_left_text).toLowerCase(Locale.getDefault()));
		} else if (dayNumber < 0) {
			textLeft.setText(getString(R.string.days_since_text).toLowerCase(Locale.getDefault()));
		}
		else{
			textLeft.setText(getString(R.string.today_text).toLowerCase(Locale.getDefault()));
			textDayLeft.setText("");
			return;
		}
			
		days = String.valueOf(Math.abs(dayNumber));
		textDayLeft.setText(days);
	}

	public void updateTextEvent(String event) {

		TextView textEvent = (TextView) getView().findViewById(R.id.text_event);
		textEvent.setText(event);
		if (event.length() > 0) {
			textEvent.setVisibility(View.VISIBLE);
		} else {
			textEvent.setVisibility(View.GONE);
		}
	}

}
