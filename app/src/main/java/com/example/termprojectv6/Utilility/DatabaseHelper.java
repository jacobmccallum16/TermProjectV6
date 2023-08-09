package com.example.termprojectv6.Utilility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.termprojectv6.Model.DataModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "ENTRIES_DATABASE";
    private static final String TABLE_NAME = "ENTRIES_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "DATE";
    private static final String COL_3 = "WEIGHT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , DATE DATE, WEIGHT FLOAT) " );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertEntry(DataModel model) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, model.getDate().toString());
        values.put(COL_3, model.getWeight());
        db.insert(TABLE_NAME, null, values);
    }

    public void updateEntry(int id, Date date, Float weight) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, date.toString());
        values.put(COL_3, weight);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }
    public void deleteEntry(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }
    public List<DataModel> getAllEntries() {
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<DataModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    DataModel task = new DataModel();
                    task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_1)));
                    task.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_2)));
                    task.setWeight(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_3)));
                    modelList.add(task);
                } while (cursor.moveToNext());
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
