package com.example.aiome.okhttp3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button mButtonGet;
    private Button mButtonPost;
    private Button mButtonUpFile;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonGet = (Button) findViewById(R.id.requestAsync);
        mButtonPost = (Button) findViewById(R.id.requestSync);
        mTextView = (TextView) findViewById(R.id.response);
        mButtonUpFile = (Button) findViewById(R.id.upFile);

        //1.创建OkHttpClient对象
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3,TimeUnit.SECONDS)
                .writeTimeout(3,TimeUnit.SECONDS)
                .build();
        //2.创建一个Request
        FormBody formBody = new FormBody.Builder()
                .add("loginName","15230106379")
                .add("password","123")
                .build();
        final Request requestPost = new Request.Builder()
                .url("http://192.168.3.106/api/account/login")
                .post(formBody)
                .build();
        final Request requestGet = new Request.Builder()
                .url("http://192.168.3.106/api/account/login")
                .build();

        File file1 = new File("/storage/emulated/0/Download/1.jpg");
        File file2 = new File("/storage/emulated/0/Download/2.jpg");
        File file3 = new File("/storage/emulated/0/Download/3.jpg");

        RequestBody upFileBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"token\""),
                        RequestBody.create(null, "212e7f098034446b9219b66918a3edc9gxWB8y"))
                .addFormDataPart("file1",file1.getName(),fileBody(file1))
                .addFormDataPart("file2",file2.getName(),fileBody(file2))
                .addFormDataPart("file3",file3.getName(),fileBody(file3))
                .build();


        final Request upFile = new Request.Builder()
                .url("http://192.168.3.106/api/fs/upload")
                .post(upFileBody)
                .build();
;

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okHttpClient.newCall(requestPost).enqueue(new Callback() {
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
                okHttpClient.newCall(requestGet).enqueue(new Callback() {
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

        mButtonUpFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okHttpClient.newCall(upFile).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        final String result = e.toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText("上传图片失败:" + result);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText("上传图片成功:" + result);
                            }
                        });
                    }
                });
            }
        });

    }

    private RequestBody fileBody(File file) {
        return RequestBody.create(MediaType.parse("application/octet-stream"),file);
    }
}
