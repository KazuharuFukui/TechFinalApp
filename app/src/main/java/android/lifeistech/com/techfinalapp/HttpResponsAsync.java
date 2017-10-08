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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ichigen on 2017/09/25.
 */

//非同期処理のための、AsyncTaskを継承したクラスを作成

public class HttpResponsAsync extends AsyncTask<Void, Void, String> {

    String category;
    CallBack callBack;

    HttpResponsAsync(String category, CallBack callBack){
        this.category = category;
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }

    public void comment (){

    }

    //通信処理を記述
    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection con = null;
        URL url = null;

        //String booksSt = "https://www.googleapis.com/books/v1/volumes?q=subject:Fiction";

        //String urlSt = "https://www.googleapis.com/books/v1/volumes?q=subject:Fiction";

        /**
         String urlBooks = "";
         //取得したいJSONデータの構造をもとに上記のパース方法いずれかを記述
         for (int i = 0; i < 9; i++) {
         urlBooks = jsonData.getJSONObject("volumeInfo").getJSONArray("categories").getString(i);
         }
         Log.d("categories", urlBooks);*/


        ArrayList<String> godiva = new ArrayList<String>();
        godiva.add("日本");
        godiva.add("ブラジル");
        godiva.add("イングランド");
        godiva.add("こころ");

        //String godiva = "dragon";

        String urlSt = "https://www.googleapis.com/books/v1/volumes?q=" + godiva.get(3) + "+" + "subject:" + category;

        Log.d("test",category);



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

        //Log.d("DEBUGGGGG", result);

        try {
            JSONObject jsonData = new JSONObject(result);

            JSONArray books = jsonData.getJSONArray("items");

            //String title = books.getJSONObject(0).getJSONObject("volumeInfo").getString("title");
                    //Log.d("DEBUG", books.getJSONObject(0).getJSONObject("volumeInfo").getString("title"));

/**
            JSONArray items = json.getJSONArray("items");
            String title = "";
            for (int i =0; i < items.length(); i++) {
                JSONObject data = items.getJSONObject(i);
                // 名前を取得
                String title = data.getString("title");
                // 年齢を取得
                String reader = data.getString("reader");
            }*/

            // 配列を取得する場合
            //JSONArray jsonArray = new JSONObject(result).getJSONArray("name");
            // String型の場合は下記


            String title = "";
            String reader = "";
            String thumbnail = "";
            String authors = "";
            String publish = "";
            String description = "";
            String categories = "";
            String publisher = "";
            String saleability = "";

            int number = jsonData.getInt("totalItems");

            Log.d("number", String.valueOf(number));

            //int number = 2;//取得済み
            int getNumber = 10;//取得したい冊数
            int n = 0;
            if (number >= getNumber){
                Random r = new Random();
                n = r.nextInt(getNumber);
                Log.d("number2", String.valueOf(n));
            } else {
                Random r = new Random();
                n = r.nextInt(number);
                Log.d("number3", String.valueOf(n));
            }

            // 1タイトル
            if(books.getJSONObject(n).getJSONObject("volumeInfo").has("title")){
                title = books.getJSONObject(n).getJSONObject("volumeInfo").getString("title");
            }

            // 2購入リンク
            if(books.getJSONObject(n).getJSONObject("saleInfo"). has("buyLink")){
                reader = books.getJSONObject(n).getJSONObject("saleInfo").getString("buyLink");
            }
            // 3書籍の画像
            if(books.getJSONObject(n).getJSONObject("volumeInfo").getJSONObject("imageLinks").has("thumbnail")){
                thumbnail = books.getJSONObject(n).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
            }

            // 4著者
            //取得したいJSONデータの構造をもとに上記のパース方法いずれかを記述
            for (int i = 0; i < 1; i++) {
                if(books.getJSONObject(n).getJSONObject("volumeInfo").has("author")){
                    authors = books.getJSONObject(n).getJSONObject("volumeInfo").getJSONArray("authors").getString(i);
                }
            }
            Log.d("著者", authors);

            // 5出版年
            if(books.getJSONObject(n).getJSONObject("volumeInfo").has("publishedDate")){
                publish = books.getJSONObject(n).getJSONObject("volumeInfo").getString("publishedDate");
            }
            // 6あらすじ
            if(books.getJSONObject(n).getJSONObject("volumeInfo").has("description")){
                description = books.getJSONObject(n).getJSONObject("volumeInfo").getString("description");
            }
            // 7分類
            if(books.getJSONObject(n).getJSONObject("volumeInfo").has("categories")){
                categories = books.getJSONObject(n).getJSONObject("volumeInfo").getString("categories");
            }
            // 8出版社
            if(books.getJSONObject(n).getJSONObject("volumeInfo").has("publisher")) {
                publisher = books.getJSONObject(n).getJSONObject("volumeInfo").getString("publisher");
            }
            // 9販売状況
            if(books.getJSONObject(n).getJSONObject("saleInfo").has("saleability")){
                saleability = books.getJSONObject(n).getJSONObject("saleInfo").getString("saleability");
            }

            /**
            // Log
            Log.d("タイトル", title);
            Log.d("概要", description);
            Log.d("売却可能性", saleability);
            Log.d("画像", thumbnail);*/


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
