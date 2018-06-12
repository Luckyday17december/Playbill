// PlaybillDatabaseHelper.java
// SQLiteOpenHelper subclass that defines the app's database
package ru.petrsu.playbill.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.petrsu.playbill.data.DatabaseDescription.Tevent;

class PlaybillDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Playbill.db";
    private static final int DATABASE_VERSION = 1;

    // constructor
    public PlaybillDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creates the tevents table when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL for creating the tevents table
        final String CREATE_TEVENTS_TABLE =
                "CREATE TABLE " + Tevent.TABLE_NAME + "(" +
                        Tevent._ID + " integer primary key, " +
                        Tevent.COLUMN_DATE + " TEXT, " +
                        Tevent.COLUMN_PRICE + " TEXT, " +
                        Tevent.COLUMN_TIME + " TEXT, " +
                        Tevent.COLUMN_PLACE + " TEXT, " +
                        Tevent.COLUMN_TITLE + " TEXT, " +
                        Tevent.COLUMN_SYNOPSIS + " TEXT, " +
                        Tevent.COLUMN_IMAGE + " TEXT);";
        db.execSQL(CREATE_TEVENTS_TABLE); // create the tevents table
    }

    // normally defines how to upgrade the database when the schema changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) { }
}
