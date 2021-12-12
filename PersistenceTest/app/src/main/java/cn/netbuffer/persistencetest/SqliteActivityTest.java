package cn.netbuffer.persistencetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import cn.netbuffer.persistencetest.component.DatabaseHelper;

public class SqliteActivityTest extends AppCompatActivity {

    private static final String TAG = "SqliteActivityTest";
    private static final String dbFileName = "persistence_test";
    private static final int dbVersion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);
    }

    public void initDatabase(View view) {
        Log.i(TAG, "initDatabase");
        buildSQLiteDatabase();
    }

    private SQLiteDatabase buildSQLiteDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this, dbFileName, null, dbVersion);
        //发起建库操作，SQLiteOpenHelper.onCreate方法得到执行，重复点击按钮不会重复执行
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase;
    }

    public void insert(View view) {
        SQLiteDatabase sqLiteDatabase = buildSQLiteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_desc", "your_item_desc");
        contentValues.put("item_real", 1.0f);
        contentValues.put("item_int", 1);
        contentValues.put("item_title", "your_item_title");
        long result = sqLiteDatabase.insert("Data", null, contentValues);
        Log.i(TAG, "insert result " + result);
    }

    public void rawQuery(View view) {
        SQLiteDatabase sqLiteDatabase = buildSQLiteDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Data", null);
        cursor.moveToFirst();
        do {
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            String itemDesc = cursor.getString(cursor.getColumnIndex("item_desc"));
            float itemReal = cursor.getFloat(cursor.getColumnIndex("item_real"));
            int itemInt = cursor.getInt(cursor.getColumnIndex("item_int"));
            String itemTitle = cursor.getString(cursor.getColumnIndex("item_title"));
            Log.i(TAG, "find data id=" + id + ",itemDesc=" + itemDesc + ",itemReal=" + itemReal + ",itemInt=" + itemInt + ",itemTitle=" + itemTitle);
        } while (cursor.moveToNext());
    }

}