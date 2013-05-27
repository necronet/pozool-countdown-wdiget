package com.pozool.widget.ticktack;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;

public class ConfigurationActivity extends Activity implements OnDateSelectedListener{

	private DateMidnight startDate, endDate;
	private CalendarPickerView calendarPickerView;
	private TextView textDayLeft;
	private int appWidgetId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
		    appWidgetId = extras.getInt(
		    		AppWidgetManager.EXTRA_APPWIDGET_ID, 
		    		AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		Log.d("Configuration", "AppWidgetId: "+ appWidgetId);

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

		RemoteViews views = new RemoteViews(getPackageName(),R.layout.ticktack);
		appWidgetManager.updateAppWidget(appWidgetId, views);

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
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.configuration, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
			case R.id.action_done:
				Intent resultValue = new Intent();
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				setResult(RESULT_OK, resultValue);
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public void onDateSelected(Date date) {
		endDate = new DateMidnight(date);
		updateCountdownUI();
	}

}
