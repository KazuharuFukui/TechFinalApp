package android.lifeistech.com.techfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView =(TextView)findViewById(R.id.textView);
        textView.setText("ようこそ");

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // インテントの作成
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                // 遷移先のアクティビティを起動
                startActivity(intent);
            }
        };
        findViewById(R.id.novelButton).setOnClickListener(buttonClickListener);


    }

}
