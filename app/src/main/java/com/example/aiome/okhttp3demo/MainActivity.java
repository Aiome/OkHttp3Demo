package com.example.aiome.okhttp3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button mButtonGet;
    private Button mButtonPost;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonGet = (Button) findViewById(R.id.requestAsync);
        mButtonPost = (Button) findViewById(R.id.requestSync);
        mTextView = (TextView) findViewById(R.id.response);

        //1.创建OkHttpClient对象
        final OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建一个Request
        FormBody formBody = new FormBody.Builder()
                .add("loginName","15230106379")
                .add("password","123")
                .build();
        final Request request = new Request.Builder()
                .url("http://192.168.3.106/api/account/login")
                .post(formBody)
                .build();
;

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText("Post请求成功:" + result);

                            }
                        });
                    }
                });
            }
        });

        mButtonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4.请求加入调度
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText("Get请求成功:" + result);
                            }
                        });
                    }
                });
            }
        });

    }
}
