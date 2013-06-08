package com.pozool.widget.ticktack;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;

public class ConfigurationFragment extends Fragment
		implements
			OnDateSelectedListener, OnDateChangedListener {

	private CalendarPickerView calendarPickerView;
	private DatePicker datePicker;
	private DateMidnight startDate, endDate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.configuration, container, false);
		

		startDate = DateMidnight.now();
		endDate = DateMidnight.now().plusDays(1);

		calendarPickerView = (CalendarPickerView) view
				.findViewById(R.id.calendar_view);

		if(calendarPickerView != null) {
			Calendar maxDate = Calendar.getInstance();
			Calendar minDate = Calendar.getInstance();
			maxDate.add(Calendar.YEAR, 2);

			calendarPickerView.init(minDate.getTime(), maxDate.getTime())
					.withSelectedDate(endDate.toDate());
			calendarPickerView.setOnDateSelectedListener(this);
		} else {
			datePicker = (DatePicker)view.findViewById(R.id.datePicker);
			datePicker.init(endDate.year().get(), endDate.monthOfYear().get()-1, endDate.dayOfMonth().get(), this);
		}

		EditText editEvent = (EditText) view.findViewById(R.id.edit_event);

		editEvent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence text, int start, int before,
					int count) {
				((ConfigurationActivity) getActivity()).updatePreviewEvent(text.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateCountdownUI();
	}

	public void updateCountdownUI() {
		Period period = new Period(startDate, endDate, PeriodType.dayTime());
		String days = String.valueOf(period.getDays());
		((ConfigurationActivity) getActivity()).updatePreviewDate(days);
	}
	
	public void selectDate(DateMidnight endDate) {
		if (calendarPickerView!=null) {
			Log.d("Configuration", "Date selected: " + endDate);
			calendarPickerView.selectDate(endDate.toDate());
		} else
			datePicker.init(endDate.year().get(), endDate.monthOfYear().get()-1, endDate.dayOfMonth().get(), this);
	}

	@Override
	public void onDateSelected(Date date) {
		endDate = new DateMidnight(date);
		updateCountdownUI();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		endDate = new DateMidnight(year, monthOfYear+1, dayOfMonth);
		updateCountdownUI();
	}
	
	public DateMidnight getEndDate() { 
		return endDate;
	}

}
