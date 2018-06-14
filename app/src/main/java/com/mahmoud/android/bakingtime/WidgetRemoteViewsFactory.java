package com.mahmoud.android.bakingtime;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private String[] mIngredients;

    public WidgetRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mIngredients = prefs.getString("desired_recipe_ingredients", "").split(";,");
        Log.d("Widget", prefs.getString("desired_recipe_ingredients", ""));
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        rv.setTextViewText(android.R.id.text1, mIngredients[position]);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
