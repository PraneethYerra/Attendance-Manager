package com.praneethcorporation.atendance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 7/16/2017.
 */

public class SubjectDbHElper extends SQLiteOpenHelper {
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + SubjectContract.SubjectEntry.TABLE_NAME + "(" + SubjectContract.SubjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SubjectContract.SubjectEntry.NAME_OF_THE_SUBJECT + " TEXT NOT NULL," + SubjectContract.SubjectEntry.NO_OF_PRESENTS + " INTEGER DEFAULT 0," + SubjectContract.SubjectEntry.NO_OF_TOTAL_CLASSES + " INTEGER DEFAULT 0,"+ SubjectContract.SubjectEntry.PERCENTAGE +" REAL DEFAULT 0);";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SubjectContract.SubjectEntry.TABLE_NAME;
    public static final String DATABASE_NAME = "Subjects.db";
    public static final int DATABASE_VERSION = 11;

    Context context;
    public SubjectDbHElper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operation","Database created");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("Database Operations","Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
