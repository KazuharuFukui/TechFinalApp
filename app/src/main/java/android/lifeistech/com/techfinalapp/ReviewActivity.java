package android.lifeistech.com.techfinalapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    TextView titleText;
    TextView descriptionText;
    TextView readerText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        HttpResponsAsync async = new HttpResponsAsync(new HttpResponsAsync.CallBack() {
            @Override
            public void onGet(Bundle bundle) {
                String title = bundle.getString("title");
                String description = bundle.getString("description");
                final String reader = bundle.getString("reader");

                Log.d("タイトル", title);
                Log.d("概要", description);

                titleText = (TextView) findViewById(R.id.titleText);
                descriptionText = (TextView) findViewById(R.id.descriptionText);
                readerText = (TextView) findViewById(R.id.readerText);

                titleText.setText(title);
                descriptionText.setText(description);
                readerText.setText(reader);


                View.OnClickListener button1ClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(reader));
                        startActivity(intent);
                    }
                };
                findViewById(R.id.readButton).setOnClickListener(button1ClickListener);
            }
        });
        async.execute();



    }


}
