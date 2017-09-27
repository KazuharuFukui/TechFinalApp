package android.lifeistech.com.techfinalapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ReviewActivity extends AppCompatActivity {

    TextView titleText;
    TextView descriptionText;
    TextView readerText;
    ImageView imageView;
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
                String thumbnail = bundle.getString("thumbnail");
                final String reader = bundle.getString("reader");

                Log.d("タイトル", title);
                Log.d("概要", description);

                titleText = (TextView) findViewById(R.id.titleText);
                descriptionText = (TextView) findViewById(R.id.descriptionText);
                readerText = (TextView) findViewById(R.id.readerText);
                imageView = (ImageView) findViewById(R.id.imageView);

                titleText.setText(title);
                descriptionText.setText(description);
                readerText.setText(reader);

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

}
