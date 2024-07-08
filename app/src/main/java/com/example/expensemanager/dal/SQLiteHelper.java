package com.example.expensemanager.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.expensemanager.model.Item;

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

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Username = ? AND Password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }





    // Lay tat ca theo thoi gian
    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = db.query("items", null, null, null, null, null, order);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }

        if (rs != null) {
            rs.close();
        }

        return list;
    }



    // Lay theo date
    public List<Item> getByDate(String date) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }
    public long addItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("items", null, values);
    }
    public int update(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return db.update("items", values, whereClause, whereArgs);
    }

    public int delete(int id) {
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(id)};

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("items", whereClause, whereArgs);
    }

    public List<Item> searchByTitle(String key) {
        List<Item> list = new ArrayList<>();
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
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }
    public List<Item> searchByCategory(String Category) {
        List<Item> list = new ArrayList<>();
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
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }
    public List<Item> searchByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();
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
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

}
