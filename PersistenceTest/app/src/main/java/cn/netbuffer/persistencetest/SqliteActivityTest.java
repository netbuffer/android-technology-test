package cn.netbuffer.persistencetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import cn.netbuffer.persistencetest.component.DatabaseHelper;

public class SqliteActivityTest extends AppCompatActivity {

    private static final String TAG = "SqliteActivityTest";
    private static final String dbFileName = "persistence_test";
    private static final int dbVersion = 1;
    private TextView textView;
    private EditText itemTitle;
    private EditText itemDesc;
    private EditText itemReal;
    private EditText itemInt;
    private EditText itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);
        textView = findViewById(R.id.raw_query_textview);
        itemTitle = findViewById(R.id.item_title);
        itemDesc = findViewById(R.id.item_desc);
        itemReal = findViewById(R.id.item_real);
        itemInt = findViewById(R.id.item_int);
        itemId = findViewById(R.id.item_id);
    }

    public void initDatabase(View view) {
        Log.i(TAG, "initDatabase");
        buildSQLiteDatabase();
    }

    public void updateDatabase(View view) {
        Log.i(TAG, "updateDatabase");
        updateDatabase();
    }

    private SQLiteDatabase buildSQLiteDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this, dbFileName, null, dbVersion);
        //发起建库操作，SQLiteOpenHelper.onCreate方法得到执行，重复点击按钮不会重复执行
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase;
    }

    private SQLiteDatabase updateDatabase() {
        //通过将数据库版本号更新,来使SQLiteOpenHelper.onUpgrade方法得到
        DatabaseHelper databaseHelper = new DatabaseHelper(this, dbFileName, null, dbVersion+1);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase;
    }

    public void insert(View view) {
        SQLiteDatabase sqLiteDatabase = buildSQLiteDatabase();
        ContentValues contentValues = buildContentValues();
        long result = sqLiteDatabase.insert("Data", null, contentValues);
        Toast.makeText(this, "insert [" + contentValues + "] succeeded", Toast.LENGTH_LONG).show();
        Log.i(TAG, "insert result " + result);
    }

    private ContentValues buildContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_desc", itemDesc.getText().toString());
        contentValues.put("item_real", Float.parseFloat(itemReal.getText().toString()));
        contentValues.put("item_int", Integer.parseInt(itemInt.getText().toString()));
        contentValues.put("item_title", itemTitle.getText().toString());
        return contentValues;
    }

    public void rawQuery(View view) {
        SQLiteDatabase sqLiteDatabase = buildSQLiteDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Data", null);
        if (!cursor.moveToFirst()) {
            Log.i(TAG, "Data table is empty");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        do {
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            String itemDesc = cursor.getString(cursor.getColumnIndex("item_desc"));
            float itemReal = cursor.getFloat(cursor.getColumnIndex("item_real"));
            int itemInt = cursor.getInt(cursor.getColumnIndex("item_int"));
            String itemTitle = cursor.getString(cursor.getColumnIndex("item_title"));
            String str = "id=" + id + ",itemDesc=" + itemDesc + ",itemReal=" + itemReal + ",itemInt=" + itemInt + ",itemTitle=" + itemTitle;
            stringBuilder.append(str).append("\n");
            Log.i(TAG, "find data " + str);
        } while (cursor.moveToNext());
        textView.setText(stringBuilder);
    }

    public void delete(View view) {
        SQLiteDatabase sqLiteDatabase = buildSQLiteDatabase();
        int result = sqLiteDatabase.delete("Data", "id=?", new String[]{itemId.getText().toString()});
        Log.i(TAG, "delete result " + result);
        Toast.makeText(this, "delete succeeded", Toast.LENGTH_LONG).show();
    }

    public void update(View view) {
        SQLiteDatabase sqLiteDatabase = buildSQLiteDatabase();
        ContentValues contentValues = buildContentValues();
        int result = sqLiteDatabase.update("Data", contentValues, "id=?", new String[]{itemId.getText().toString()});
        Log.i(TAG, "update result " + result);
        Toast.makeText(this, "update succeeded", Toast.LENGTH_LONG).show();
    }

    public void deleteAll(View view) {
        SQLiteDatabase sqLiteDatabase = buildSQLiteDatabase();
        sqLiteDatabase.execSQL("delete from Data");
        Toast.makeText(this, "deleteAll succeeded", Toast.LENGTH_LONG).show();
    }
}