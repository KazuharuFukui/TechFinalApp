package android.lifeistech.com.techfinalapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ReviewActivity extends AppCompatActivity {

    TextView titleText;//1
    TextView readerText;//2
    TextView authorsText;//4
    TextView publishText;//5
    TextView descriptionText;//6
    //TextView categoriesText;//7
    TextView publisherText;//8
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        HttpResponsAsync async = new HttpResponsAsync(new HttpResponsAsync.CallBack() {
            @Override
            public void onGet(Bundle bundle) {
                String title = bundle.getString("title");
                String authors = bundle.getString("authors");
                String publish = bundle.getString("publish");
                String description = bundle.getString("description");
                String thumbnail = bundle.getString("thumbnail");
                String publisher = bundle.getString("publisher");
                final String reader = bundle.getString("reader");

                Log.d("タイトル", title);
                Log.d("概要", description);

                titleText = (TextView) findViewById(R.id.titleText);
                readerText = (TextView) findViewById(R.id.readerText);
                authorsText = (TextView) findViewById(R.id.authorsText);
                publishText = (TextView) findViewById(R.id.publishText);
                descriptionText = (TextView) findViewById(R.id.descriptionText);
                publisherText = (TextView) findViewById(R.id.publisherText);

                imageView = (ImageView) findViewById(R.id.imageView);

                titleText.setText(title);
                readerText.setText(reader);
                authorsText.setText(authors);
                publishText.setText(publish);
                descriptionText.setText(description);
                publisherText.setText(publisher);


                // 画像の追加
                new AsyncTask<String, Void, Bitmap>() {

                    // 非同期処理
                    @Override
                    protected Bitmap doInBackground(String... params) {
                        Bitmap image = null;
                        BitmapFactory.Options options;
                        try {
                            // インターネット上の画像を取得して、Bitmap に変換
                            URL url = new URL(params[0]);
                            options = new BitmapFactory.Options();
                            // 実際に読み込む
                            options.inJustDecodeBounds = false;
                            InputStream is = (InputStream) url.getContent();
                            image = BitmapFactory.decodeStream(is, null, options);
                            is.close();
                        } catch (Exception e) {
                            Log.i("button", e.getMessage());
                        }
                        return image;
                    }

                    // UI スレッド処理
                    @Override
                    protected void onPostExecute(Bitmap image) {
                        super.onPostExecute(image);

                        // Bitmap 取得に成功している場合は表示します
                        if (image != null) {
                            ImageView imageView = (ImageView) ReviewActivity.this.findViewById(R.id.imageView);
                            imageView.setImageBitmap(image);
                        }

                    }
                }.execute(thumbnail);   // 画像のURL



                //　ボタンクリック時に本を試し読みする
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean result = true;

        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }
}
