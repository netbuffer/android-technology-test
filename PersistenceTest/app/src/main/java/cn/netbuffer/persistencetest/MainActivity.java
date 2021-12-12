package cn.netbuffer.persistencetest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String fileName = "file_test";
    private static final String TAG = "MainActivity";
    private EditText editText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.your_text);
        sharedPreferences = getSharedPreferences("persistence_test", Context.MODE_PRIVATE);
    }

    /**
     * openFileOutput/openFileInput的使用不需要在AndroidManifest.xml中声明文件权限
     *
     * @param view
     */
    public void writeFile(View view) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            IOUtils.write(editText.getText().toString(), fileOutputStream, Charset.defaultCharset());
        } catch (FileNotFoundException e) {
            Log.i(TAG, "openFileOutput FileNotFoundException=" + e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, "openFileOutput IOException=" + e.getMessage());
        }
    }

    public void readFile(View view) {
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            editText.setText(IOUtils.toString(fileInputStream, Charset.defaultCharset()));
        } catch (FileNotFoundException e) {
            Log.i(TAG, "openFileInput FileNotFoundException=" + e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, "openFileInput IOException=" + e.getMessage());
        }
    }

    public void spWriteKey(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("your_key", editText.getText().toString());
        editor.apply();
    }

    public void spReadKey(View view) {
        editText.setText(sharedPreferences.getString("your_key", ""));
    }

    public void sqlite(View view) {
        startActivity(new Intent(this, SqliteActivityTest.class));
    }

}