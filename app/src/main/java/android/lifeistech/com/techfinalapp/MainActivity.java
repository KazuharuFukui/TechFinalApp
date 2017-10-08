package android.lifeistech.com.techfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
        // ツールバーをアクションバーとしてセット
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // アイコンを指定
        toolbar.setNavigationIcon(R.mipmap.ic_launcher_round);*/

        /**
        HttpResponsAsync async = new HttpResponsAsync(new HttpResponsAsync.CallBack() {
            @Override
            public void onGet(Bundle bundle) {
                String title = bundle.getString("title");
                Log.d("タイトル", title);
                textView = (TextView) findViewById(R.id.titleText);
                textView.setText(title);
            }
        });
        async.execute();*/
/**
        textView =(TextView)findViewById(R.id.textView);
        textView.setText("ようこそ");*/

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // インテントの作成
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                //　インテントに値をセット
                intent.putExtra("keyword", "Fiction");
                // 遷移先のアクティビティを起動
                startActivity(intent);
            }
        };
        findViewById(R.id.novelButton).setOnClickListener(buttonClickListener);

        View.OnClickListener buttonClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // インテントの作成
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                //　インテントに値をセット
                intent.putExtra("keyword", "Cook");
                // 遷移先のアクティビティを起動
                startActivity(intent);
            }
        };
        findViewById(R.id.cookButton).setOnClickListener(buttonClickListener1);

        View.OnClickListener buttonClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // インテントの作成
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                //　インテントに値をセット
                intent.putExtra("keyword", "Literary Collections / Asian / Japanese");
                // 遷移先のアクティビティを起動
                startActivity(intent);
            }
        };
        findViewById(R.id.anotherButton).setOnClickListener(buttonClickListener2);


    }

}
