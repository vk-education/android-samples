package ru.hse.lection04.businesslayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ru.hse.lection04.datalayer.LogDataAccessor;
import ru.hse.lection04.objects.LogEntry;

/**
 * Класс для работы с логами. Обеспечивает так же подписку на изменение данных
 */
public class LogProvider extends AbstractCallbackProvider<LogProvider.IListener> {
    public static final String TAG = "LogProvider";

    protected static final Handler HANDLER_MAIN = new Handler(Looper.getMainLooper());
    protected static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);

    /**
     * Подписчик, который будет уведомляться, когда прозишли изменения данных
     */
    public interface IListener {
        public void logUpdated(LogProvider provider, List<LogEntry> entries);
    }


    /**
     * Внутренний класс для остлеживания обновления ContentProvider с логами
     */
    protected final UpdateListener mUpdateListener = new UpdateListener();

    protected final Context mContext;


    public LogProvider(Context context) {
        mContext = context;
    }

    @Override
    protected void setActivation(boolean value) {
        super.setActivation(value);

        if (value) {
            mContext.getContentResolver().registerContentObserver(LogDataAccessor.CONTENT_URI, true, mUpdateListener);
        } else {
            mContext.getContentResolver().unregisterContentObserver(mUpdateListener);
        }
    }


    /**
     * Добавить запись в лог
     * @param time время события
     * @param message текст события
     */
    public void write(Date time, String message) {
        final ContentValues values = new ContentValues();
        values.put(LogDataAccessor.COLUMN_DATE, DATE_FORMATTER.format(time));
        values.put(LogDataAccessor.COLUMN_TYPE, message);

        mContext.getContentResolver().insert(LogDataAccessor.CONTENT_URI, values);
    }


    /**
     * Получить список логов
     * @return Список из объектов LogEntry
     */
    protected List<LogEntry> all() {
        final Cursor cursor = mContext.getContentResolver().query(LogDataAccessor.CONTENT_URI, null, null, null, null);

        final List<LogEntry> result = new ArrayList<>();

        cursor.move(-1);
        while (cursor.moveToNext()) {
            final LogEntry item = new LogEntry();

            final int dateIndex = cursor.getColumnIndex(LogDataAccessor.COLUMN_DATE);
            item.time = cursor.getString(dateIndex);

            final int typeIndex = cursor.getColumnIndex(LogDataAccessor.COLUMN_TYPE);
            item.message = cursor.getString(typeIndex);

            result.add(item);
        }

        return result;
    }

    protected void notifyListeners() {
        HANDLER_MAIN.post(new ListenerNotificator(this, mListeners, all()));
    }


    /**
     * Подписчик на изменения ContentProvider
     */
    protected class UpdateListener extends ContentObserver {
        public UpdateListener() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            notifyListeners();
        }
    }

    /**
     * Класс для обновления слушателей
     */
    protected static class ListenerNotificator implements Runnable {
        protected final LogProvider mLogProvider;
        protected final Set<IListener> mListeners;
        protected final List<LogEntry> mEntries;


        public ListenerNotificator(LogProvider logProvider, Set<IListener> listeners, List<LogEntry> entries) {
            mLogProvider = logProvider;
            mListeners = listeners;
            mEntries = entries;
        }


        @Override
        public void run() {
            for (IListener listener: mListeners) {
                listener.logUpdated(mLogProvider, mEntries);
            }
        }
    }
}
