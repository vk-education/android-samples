package ru.hse.lection04.datalayer;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class LogDataAccessor extends ContentProvider {
    public static final String AUTHORITY = "ru.hse.lection04.log";
    public static final String CONTENT_PATH = "entry";

    protected static final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    protected static HashMap<String, String> PROJECTION_MAP;

    protected static final int TYPE_ALL = 0;
    protected static final int TYPE_ITEM = 1;

    protected static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/vnd.ru.hse.lection04.all";
    protected static final String MIME_TYPE_ITEM = "vnd.android.cursor.dir/vnd.ru.hse.lection04.item";

    protected static final String DATABASE_NAME = "connectivity_log.sqlite";
    protected static final String TABLE_NAME = "CONNECTIVITY";

    protected static final String _ID = "_id";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TYPE = "TYPE";



    protected static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH) {
        {
            addURI(AUTHORITY, CONTENT_PATH, TYPE_ALL);
            addURI(AUTHORITY, CONTENT_PATH + "/#", TYPE_ITEM);
        }
    };


    protected SQLiteDatabase mDatabase;


    @Override
    public boolean onCreate() {
        final DatabaseHelper helper = new DatabaseHelper(getContext());
        mDatabase = helper.getWritableDatabase();

        if (mDatabase == null) {
            return false;
        }

        // Уведоиляем, что провайдер может работать. Иначе запуск приложения будет остановлен
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case TYPE_ALL:
                return MIME_TYPE_DIR;

            case TYPE_ITEM:
                return MIME_TYPE_ITEM;

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID = mDatabase.insert(TABLE_NAME, "", values);

        if (rowID > 0) {
            final Uri newUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (URI_MATCHER.match(uri)) {
            case TYPE_ALL:
                count = mDatabase.delete(TABLE_NAME, selection, selectionArgs);
                break;

            case TYPE_ITEM:
                String id = uri.getPathSegments().get(1);
                count = mDatabase.delete(TABLE_NAME, _ID +  " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (URI_MATCHER.match(uri)) {
            case TYPE_ALL:
                count = mDatabase.update(TABLE_NAME, values, selection, selectionArgs);
                break;

            case TYPE_ITEM:
                count = mDatabase.update(TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_NAME);

        switch (URI_MATCHER.match(uri)) {
            case TYPE_ALL:
                builder.setProjectionMap(PROJECTION_MAP);
                break;

            case TYPE_ITEM:
                builder.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = COLUMN_DATE;
        }

        final Cursor cursor = builder.query(mDatabase, projection,	selection, selectionArgs,null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    protected static class DatabaseHelper extends SQLiteOpenHelper {
        static final int DATABASE_VERSION = 1;
        static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME +
                " (" +
                _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_TYPE + " TEXT NOT NULL" +
                ");";

        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
            onCreate(db);
        }
    }
}
