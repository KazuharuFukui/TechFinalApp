package android.lifeistech.com.techfinalapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ichigen on 2017/09/25.
 */

//非同期処理のための、AsyncTaskを継承したクラスを作成

public class HttpResponsAsync extends AsyncTask<Void, Void, String> {

    CallBack callBack;

    HttpResponsAsync(CallBack callBack){
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }

    //通信処理を記述
    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection con = null;
        URL url = null;
        String urlSt = "https://www.googleapis.com/books/v1/volumes/Jb3GNmCzcIoC";

        try {
            // URLの作成
            url = new URL(urlSt);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection) url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("GET");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            con.setDoOutput(false);

            // 接続
            con.connect(); // ①

            /**
             // 本文の取得
             InputStream in = con.getInputStream();
             byte bodyByte[] = new byte[1024];
             in.read(bodyByte);
             System.out.println("read:" + bodyByte );
             in.close();
             */
            InputStream in = con.getInputStream();
            String readSt = readInputStream(in);

            return readSt;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // doInBackground後処理

        Log.d("DEBUGGGGG", result);

        try {
            JSONObject jsonData = new JSONObject(result);
            // 配列を取得する場合
            //JSONArray jsonArray = new JSONObject(result).getJSONArray("name");
            // String型の場合は下記

            // 1タイトル
            String title = jsonData.getJSONObject("volumeInfo").getString("title");
            // 2購入リンク
            String reader = jsonData.getJSONObject("saleInfo").getString("buyLink");
            // 3書籍の画像
            String thumbnail = jsonData.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");

            // 4著者
            String authors = "";
            //取得したいJSONデータの構造をもとに上記のパース方法いずれかを記述
            for (int i = 0; i < 1; i++) {
                authors = jsonData.getJSONObject("volumeInfo").getJSONArray("authors").getString(i);
            }
            Log.d("著者", authors);

            // 5出版年
            String publish = jsonData.getJSONObject("volumeInfo").getString("publishedDate");
            // 6あらすじ
            String description = jsonData.getJSONObject("volumeInfo").getString("description");
            // 7分類
            String categories = jsonData.getJSONObject("volumeInfo").getString("categories");
            // 8出版社
            String publisher = jsonData.getJSONObject("volumeInfo").getString("publisher");
            // 9販売状況
            String saleability = jsonData.getJSONObject("saleInfo").getString("saleability");

            // Log
            Log.d("タイトル", title);
            Log.d("概要", description);
            Log.d("売却可能性", saleability);
            Log.d("画像", thumbnail);

            // bundle
            Bundle bundle = new Bundle();
            bundle.putString("title", title);//1
            //bundle.putString("reader", reader);//2
            bundle.putString("thumbnail", thumbnail);//3


            bundle.putString("authors", authors);//4
            bundle.putString("publish", publish);//5
            bundle.putString("description", description);//6
            bundle.putString("categories", categories);//7
            bundle.putString("publisher", publisher);//8
            bundle.putString("saleability", saleability);//9・一応

            // 2readerの追加
            if (saleability.equals("FREE")) {
                bundle.putString("reader", reader);
            } else {
                bundle.putString("reader", "https://life-is-tech.com/");
            }

            callBack.onGet(bundle);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String readInputStream(InputStream in) throws IOException {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while((st = br.readLine()) != null)
        {
            sb.append(st);
        }
        try
        {
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public interface CallBack{
        void onGet(Bundle bundle);
    }
}
