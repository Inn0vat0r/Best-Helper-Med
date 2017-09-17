package com.example.myapps.meditrack.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MediTrack_DB.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MEDICINE_DOSE_TABLE = "CREATE TABLE " + MedicineDoseContract.MedicineDoseEntry.TABLE_NAME + " (" +
                MedicineDoseContract.MedicineDoseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
              //  MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_ID + " INTEGER NOT NULL , " +
                MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_NAME + " TEXT NOT NULL , " +
                MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_FREQUENCY + " INTEGER NOT NULL , " +
                MedicineDoseContract.MedicineDoseEntry.COLUMN_NUMBER_OF_DOSE + " INTEGER NOT NULL , " +
                MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_QUANTITY + " INTEGER NOT NULL , " +
                MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_TIME + " TEXT NOT NULL , " +
                MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_PURCHASED_NUM + " INTEGER NOT NULL " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICINE_DOSE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MedicineDoseContract.MedicineDoseEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
