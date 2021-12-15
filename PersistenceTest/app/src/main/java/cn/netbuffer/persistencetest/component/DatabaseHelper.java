package cn.netbuffer.persistencetest.component;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_DATA_TABLE_SQL = "create table Data ("
            + "id integer primary key autoincrement, "
            + "item_desc text, "
            + "item_real real, "
            + "item_int integer, "
            + "item_title text)";

    public static final String CREATE_LOG_TABLE_SQL = "create table t_log ("
            + "id integer primary key autoincrement, "
            + "create_time integer, "
            + "type text)";

    private Context context;

    public DatabaseHelper(Context context, String dbFileName,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, dbFileName, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA_TABLE_SQL);
        Toast.makeText(context, "Create Table[Data] succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_LOG_TABLE_SQL);
        Toast.makeText(context, "update Table[Data] succeeded", Toast.LENGTH_SHORT).show();
    }

}