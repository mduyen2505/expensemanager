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
    private static final int DATABASE_VERSION = 2;

    public SQLiteHelper(@Nullable Context context) {
        super(context, "ChiTieu.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng items
        String sqlItems = "CREATE TABLE items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, " +
                "category TEXT, " +
                "price TEXT, " +
                "date TEXT)";
        db.execSQL(sqlItems);

        // Tạo bảng User
        String sqlUser = "CREATE TABLE User (" +
                "Username TEXT NOT NULL, " +
                "Password TEXT NOT NULL)";
        db.execSQL(sqlUser);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
        // Handle database upgrade as needed
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        super.onOpen(db);
    }
    public boolean insertUser( String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", username);
        contentValues.put("Password", password);
        long result = db.insert("User", null, contentValues);
        db.close();
        return result != -1;
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

    public List<RecycleViewAdapter.Item> searchByTitle(String key) {
        List<RecycleViewAdapter.Item> list = new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date =rs.getString(4);
            list.add(new RecycleViewAdapter.Item(id, title, category, price, date));
        }
        return list;
    }
    public List<RecycleViewAdapter.Item> searchByCategory(String Category) {
        List<RecycleViewAdapter.Item> list = new ArrayList<>();
        String whereClause = "Category like ?";
        String[] whereArgs = {Category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date =rs.getString(4);
            list.add(new RecycleViewAdapter.Item(id, title, category, price, date));
        }
        return list;
    }
    public List<RecycleViewAdapter.Item> searchByDateFromTo(String from, String to) {
        List<RecycleViewAdapter.Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(),to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date =rs.getString(4);
            list.add(new RecycleViewAdapter.Item(id, title, category, price, date));
        }
        return list;
    }

}
