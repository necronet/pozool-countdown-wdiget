package com.pozool.widget.ticktack;

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
		TextView textDayLeft = (TextView) getView().findViewById(
				R.id.text_day_left);
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
