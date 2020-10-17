package ru.hse.lection04.businesslayer;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.hse.lection04.objects.LogEntry;

public class LogProvider extends AbstractCallbackProvider<LogProvider.IListener> {
    public static final String TAG = "LogProvider";


    public interface IListener {
        public void logUpdated(LogProvider provider, List<LogEntry> entries);
    }


    protected final List<LogEntry> mTemp = new ArrayList<>();

    protected final Context mContext;


    public LogProvider(Context context) {
        mContext = context;
    }

    public void write(Date time, String message) {
        final LogEntry entry = new LogEntry();
        entry.time = time;
        entry.message = message;


        mTemp.add(entry);
        Log.d(TAG, entry.time.toString() + " - " + entry.message);

        for (IListener listener: mListeners) {
            listener.logUpdated(this, mTemp);
        }
    }
}
