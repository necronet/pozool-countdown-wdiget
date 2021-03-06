package com.pozool.widget.ticktack;

import java.util.Locale;

import org.joda.time.DateMidnight;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class TickTackWidgetProvider extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		Log.d("Widget", "ticktack update widget");
		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		

		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			DateMidnight endDate = (DateMidnight) LocalPersistence
					.readObjectFromFile(context, String.valueOf(appWidgetId));
			
			updateWidget(context,appWidgetId, endDate);
			
		}
	}
	public static void updateWidget(Context context, int appWidgetId,
			DateMidnight endDate) {

		// Create an Intent to launch ExampleActivity
		Intent intent = new Intent(context, ConfigurationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		if (endDate != null)
			intent.putExtra("endDate", endDate.toString());
		PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		DateMidnight startDate = DateMidnight.now();
		Period period = new Period(startDate, endDate, PeriodType.dayTime());
		String days = String.valueOf(period.getDays());

		int dayNumber = Integer.parseInt(days);
		String textDays = TextSink.init(context).parse(dayNumber).toUpperCase(Locale.getDefault());
		// Get the layout for the App Widget and attach an on-click listener
		// to the button

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.ticktack);
		
		views.setOnClickPendingIntent(R.id.widget_content, pendingIntent);

		views.setTextViewText(R.id.textDays,
				String.valueOf(Math.abs(dayNumber)));
		views.setTextViewText(R.id.textDaysText, textDays);

		// Tell the AppWidgetManager to perform an update on the current app
		// widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
	
	public void onEnabled(Context context) {
		scheduleAlarm(context);
	}
	
	private void scheduleAlarm(Context context) {
		
		ComponentName componentName = new ComponentName(context.getPackageName(), getClass().getName());
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

		Intent intent = new Intent(context, TickTackWidgetProvider.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetManager.getAppWidgetIds(componentName));

		PendingIntent operation = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		DateMidnight midnight = DateMidnight.now().withDayOfMonth(1);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, midnight.getMillis(), AlarmManager.INTERVAL_DAY, operation);
	}
	
	

}