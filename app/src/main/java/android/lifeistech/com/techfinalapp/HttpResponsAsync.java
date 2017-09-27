package android.lifeistech.com.techfinalapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
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

        //Log.d("DEBUGGGGG", result);


        try {
            JSONObject jsonData = new JSONObject(result);
            // 配列を取得する場合
            //JSONArray jsonArray = new JSONObject(result).getJSONArray("name");

            // String型の場合
            String title = jsonData.getJSONObject("volumeInfo").getString("title");
            String description = jsonData.getJSONObject("volumeInfo").getString("description");
            String reader = jsonData.getJSONObject("saleInfo").getString("buyLink");
            String saleability = jsonData.getJSONObject("saleInfo").getString("saleability");

            Log.d("タイトル", title);
            Log.d("概要", description);

            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("description", description);

            if (saleability == "FREE") {
                bundle.putString("reader", reader);
            } else {
                bundle.putString("reader", "http://okusurinojikan.gozaru.jp/what.html");
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
