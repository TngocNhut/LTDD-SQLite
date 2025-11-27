package com.tngocnhat.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notes.db";
    public static final String TABLE_NAME = "Notes";
    public static final String KEY_ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                CONTENT + " TEXT)";
        db.execSQL(sql);

        ContentValues ct = new ContentValues();
        ct.put(TITLE, "Ví dụ");
        ct.put(CONTENT, "Dòng ghi chú mẫu.");
        db.insert(TABLE_NAME, null, ct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Notes> getAllNotes() {
        ArrayList<Notes> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Notes n = new Notes(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                list.add(n);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public void addNotes(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put(TITLE, notes.getTitle());
        ct.put(CONTENT, notes.getContent());
        db.insert(TABLE_NAME, null, ct);
    }

    public void updateNotes(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put(TITLE, notes.getTitle());
        ct.put(CONTENT, notes.getContent());
        db.update(TABLE_NAME, ct, KEY_ID + " = ?", new String[]{String.valueOf(notes.getId())});
    }

    public void deleteNotes(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
