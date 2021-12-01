package cn.netbuffer.ttstest;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private EditText editText;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.text_for_speech);
        initTextToSpeech();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        textToSpeech.shutdown();
    }

    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i(TAG, "initTextToSpeech onInit: status=" + status);
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "LANG_MISSING_DATA Or LANG_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        textToSpeech.setPitch(1.0f);
        textToSpeech.setSpeechRate(1.0f);
    }

    public void testSpeech(View view) {
        Bundle params = new Bundle();
        params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 0.1f);
        textToSpeech.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, params, null);
    }
}