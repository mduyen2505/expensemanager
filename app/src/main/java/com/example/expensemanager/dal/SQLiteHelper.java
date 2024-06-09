package com.example.expensemanager.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.expensemanager.adapter.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChiTieu.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, " +
                "category TEXT, " +
                "price TEXT, " +
                "date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        super.onOpen(db);
    }

    // Lay tat ca theo thoi gian
    public List<RecycleViewAdapter.Item> getAll() {
        List<RecycleViewAdapter.Item> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = db.query("items", null, null, null, null, null, order);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new RecycleViewAdapter.Item(id, title, category, price, date));
        }

        if (rs != null) {
            rs.close();
        }

        return list;
    }

    public long addItem(RecycleViewAdapter.Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("items", null, values);
    }

    // Lay theo date
    public List<RecycleViewAdapter.Item> getByDate(String date) {
        List<RecycleViewAdapter.Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new RecycleViewAdapter.Item(id, title, category, price, date));
        }
        return list;
    }

    public void update(RecycleViewAdapter.Item item) {
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(item.getId())};
        sqLiteDatabase.update("items", values, whereClause, whereArgs);
    }

    public void delete(int id) {
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(id)};

        SQLiteDatabase db = getWritableDatabase();
        db.delete("items", whereClause, whereArgs);
    }
}
