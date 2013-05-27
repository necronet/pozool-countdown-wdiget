package com.pozool.widget.ticktack;

import org.joda.time.DateMidnight;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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
		DateMidnight startDate = DateMidnight.now();

		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			// Create an Intent to launch ExampleActivity
			Intent intent = new Intent(context, ConfigurationActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);

			DateMidnight endDate = (DateMidnight) LocalPersistence
					.readObjectFromFile(context, String.valueOf(appWidgetId));
			Period period = new Period(startDate, endDate, PeriodType.dayTime());
			String days = String.valueOf(period.getDays());

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.ticktack);

			views.setTextViewText(R.id.textDays, days);

			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}