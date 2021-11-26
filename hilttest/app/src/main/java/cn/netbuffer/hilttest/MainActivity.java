package cn.netbuffer.hilttest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import javax.inject.Inject;
import cn.netbuffer.hilttest.component.TextProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    @Inject
    TextProvider textProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.set_btn);
        textView = findViewById(R.id.text_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textProvider.create(10);
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                textView.setText(text);
            }
        });
    }

}