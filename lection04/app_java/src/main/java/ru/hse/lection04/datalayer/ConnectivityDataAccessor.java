package ru.hse.lection04.datalayer;

import android.content.Context;
import android.content.SharedPreferences;

public class ConnectivityDataAccessor {
    protected static final String PREFERENCES_NAME = "PREFERENCES_CONNECTIVITY";

    protected static final String KEY_TRACK_ENABLED = "TRACK_ENABLED";
    protected static final boolean VALUE_TRACK_ENABLED_DEFAULT = false;


    protected SharedPreferences mPreferences;

    protected boolean isTrackEnabled;


    public ConnectivityDataAccessor(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        initialize();
    }


    public boolean isTrackEnabled() {
        return isTrackEnabled;
    }

    public void setTrackEnabled(boolean value) {
        mPreferences.edit()
                .putBoolean(KEY_TRACK_ENABLED, value)
                .apply();

        isTrackEnabled = value;
    }

    protected void initialize() {
        isTrackEnabled = mPreferences.getBoolean(KEY_TRACK_ENABLED, VALUE_TRACK_ENABLED_DEFAULT);
    }
}
