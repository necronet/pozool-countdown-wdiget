package com.pozool.widget.ticktack;

import org.joda.time.DateMidnight;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;

public class ConfigurationActivity extends FragmentActivity{

	private int appWidgetId;
	private String days;

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
				ConfigurationFragment fragment = (ConfigurationFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentConfiguration);
				DateMidnight endDate = fragment.getEndDate();
				LocalPersistence.witeObjectToFile(this, endDate, String.valueOf(appWidgetId));

				TickTackWidgetProvider.updateWidget(this, appWidgetId, endDate);

				Intent resultValue = new Intent();
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				setResult(RESULT_OK, resultValue);
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void updatePreviewDate(String days) {
		PreviewFragment preview = (PreviewFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentPreview);
		this.days = days;
		if (preview != null) {
			preview.updateDate(days);
		}
	}



	public void updatePreviewEvent(String event) {
		PreviewFragment preview = (PreviewFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentPreview);
		if (preview != null) {
			preview.updateTextEvent(event);
		}
	}

}
