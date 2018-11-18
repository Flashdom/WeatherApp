package com.example.weatherapp.weatherapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db"; // название бд
    private static final int DATABASE_VERSION = 1; // версия базы данных
    static final String TABLE_NOTES = "notes"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NOTE = "note";
    static final String COLUMN_TITLE = "title";

    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOTE + " TEXT ," +
                COLUMN_TITLE + " TEXT " +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)) {
            String upgradeQuery = "ALTER TABLE " + TABLE_NOTES + " ADD COLUMN " + COLUMN_TITLE + " TEXT";
            db.execSQL(upgradeQuery);
        }
    }
}