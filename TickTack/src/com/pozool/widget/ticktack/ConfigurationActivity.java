package com.pozool.widget.ticktack;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;

public class ConfigurationActivity extends Activity implements OnDateSelectedListener{

	private DateMidnight startDate, endDate;
	private CalendarPickerView calendarPickerView;
	private TextView textDayLeft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		
		startDate = DateMidnight.now();
		endDate = DateMidnight.now().plusDays(1);
		
		Calendar maxDate = Calendar.getInstance();
		Calendar minDate = Calendar.getInstance();
		maxDate.add(Calendar.YEAR, 2);

		calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
		calendarPickerView.init(minDate.getTime(), maxDate.getTime()).withSelectedDate(endDate.toDate());
		calendarPickerView.setOnDateSelectedListener(this);
		
		EditText editEvent = (EditText)findViewById(R.id.edit_event);
		
		editEvent.setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) {
		        	TextView textEvent = (TextView)findViewById(R.id.text_event);
		        	textEvent.clearComposingText();
		        }
		        return false;
		    }
		});
		
		editEvent.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence text, int start, int before, int count) {
				TextView textEvent = (TextView)findViewById(R.id.text_event);
				textEvent.setText(text);
				if(text.length() > 0) {
					textEvent.setVisibility(View.VISIBLE);
				}
				else { 
					textEvent.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});

		updateCountdownUI();
	}
		
	public void updateCountdownUI() {
		Period period = new Period(startDate, endDate, PeriodType.dayTime());
		
		textDayLeft = (TextView)findViewById(R.id.text_day_left);
		textDayLeft.setText(String.valueOf(period.getDays()));
	}
	

	@Override
	public void onDateSelected(Date date) {
		endDate = new DateMidnight(date);
		updateCountdownUI();
	}

}
