package com.mahmoud.android.bakingtime;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RecipesWidget extends AppWidgetProvider {
    public static final String WIDGET_IDS_KEY ="recipe_widget_provider_widget_ids";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(WIDGET_IDS_KEY)) {
            int[] ids = intent.getExtras().getIntArray(WIDGET_IDS_KEY);
            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
        } else
            super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
            views.setRemoteAdapter(R.id.widget_list_view, serviceIntent);

            String recipeName = PreferenceManager.getDefaultSharedPreferences(context).getString("desired_recipe_name", "");
            views.setTextViewText(R.id.recipe_name, recipeName == "" ? context.getString(R.string.widget_no_recipe_selected) : recipeName);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

