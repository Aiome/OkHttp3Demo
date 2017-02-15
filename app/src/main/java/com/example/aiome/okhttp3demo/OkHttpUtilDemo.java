package com.example.aiome.okhttp3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Aiome on 2017/2/14.
 */

public class OkHttpUtilDemo extends AppCompatActivity {
    private Button mUploadFilesWithParam;
    private Button mUploadFileWithParam;
    private Button mUploadFiles;
    private Button mGet;
    private Button mPost;
    private TextView mResult;
    private Button mUploadFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        OkHttpUtil.getInstance().isLog(true);

        findView();

        mUploadFiles.setOnClickListener(mOnClickListener);
        mUploadFile.setOnClickListener(mOnClickListener);
        mGet.setOnClickListener(mOnClickListener);
        mPost.setOnClickListener(mOnClickListener);
        mUploadFilesWithParam.setOnClickListener(mOnClickListener);
        mUploadFileWithParam.setOnClickListener(mOnClickListener);

    }

    private void findView() {
        mUploadFile = (Button) findViewById(R.id.uploadFile);
        mUploadFiles = (Button) findViewById(R.id.uploadFiles);
        mGet = (Button) findViewById(R.id.get);
        mPost = (Button) findViewById(R.id.post);
        mResult = (TextView) findViewById(R.id.result);
        mUploadFilesWithParam = (Button) findViewById(R.id.uploadFilesWithParam);
        mUploadFileWithParam = (Button) findViewById(R.id.uploadFileWithParam);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.post:
                    post();
                    break;
                case R.id.get:
                    get();
                    break;
                case R.id.uploadFile:
                    uploadFile();
                    break;
                case R.id.uploadFiles:
                    uploadFiles();
                    break;
                case R.id.uploadFilesWithParam:
                    uploadFilesWithParam();
                    break;
                case R.id.uploadFileWithParam:
                    uploadFileWithParam();
                    break;
                default:
                    break;
            }
        }
    };

    private void get() {
        OkHttpUtil.getInstance().getAsync("http://192.168.178.2/api/account/login", new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(final String response) {
                Log.d("OkHttpUtilDemo",response);
                mResult.setText(response);
            }
        },
        new OkHttpUtil.Param[]{
                new OkHttpUtil.Param("loginName","15230106379"),
                new OkHttpUtil.Param("password","123")
        });
    }

    private void post() {
        OkHttpUtil.getInstance().postAsync("http://192.168.178.2/api/account/login", new OkHttpUtil.ResultCallback<String>() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(final String response) {
                        Log.d("OkHttpUtilDemo",response);
                        mResult.setText(response);
                    }
                },
                new OkHttpUtil.Param[]{
                        new OkHttpUtil.Param("loginName","15230106379"),
                        new OkHttpUtil.Param("password","123")
                });
    }

    private void uploadFileWithParam() {
        File file = new File("/storage/emulated/0/Download/1.jpg");
        try {
            OkHttpUtil.getInstance().postAsyncFile("http://192.168.81.2/api/fs/upload", file, "测试", new OkHttpUtil.ResultCallback<String>() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(final String response) {
                    Log.d("OkHttpUtilDemo",response);

                    mResult.setText(response);

                }
            },
            new OkHttpUtil.Param[]{
                    new OkHttpUtil.Param("param1","111")
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFilesWithParam() {
        File []files = {
                new File("/storage/emulated/0/Download/1.jpg"),
                new File("/storage/emulated/0/Download/2.jpg"),
                new File("/storage/emulated/0/Download/3.jpg")
        };
        String []fileKey = {
                "图片1",
                "图片2",
                "图片3"
        };
        try {
            OkHttpUtil.getInstance().postAsyncFile("http://192.168.81.2/api/fs/upload", files, fileKey, new OkHttpUtil.ResultCallback<String>() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(final String response) {
                    Log.d("OkHttpUtilDemo",response);

                    mResult.setText(response);

                }
            },
            new OkHttpUtil.Param[]{
                    new OkHttpUtil.Param("param1","111"),
                    new OkHttpUtil.Param("param2","222"),
                    new OkHttpUtil.Param("param3","333"),
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFiles() {
        File []files = {
                new File("/storage/emulated/0/Download/1.jpg"),
                new File("/storage/emulated/0/Download/2.jpg"),
                new File("/storage/emulated/0/Download/3.jpg")
        };
        String []fileKey = {
                "图片1",
                "图片2",
                "图片3"
        };
        try {
            OkHttpUtil.getInstance().postAsyncFile("http://192.168.81.2/api/fs/upload", files, fileKey, new OkHttpUtil.ResultCallback<String>() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(final String response) {
                    Log.d("OkHttpUtilDemo",response);

                    mResult.setText(response);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFile() {
        File file = new File("/storage/emulated/0/Download/1.jpg");
        try {
            OkHttpUtil.getInstance().postAsyncFile("http://192.168.81.2/api/fs/upload", file, "测试", new OkHttpUtil.ResultCallback<String>() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(final String response) {
                    Log.d("OkHttpUtilDemo",response);

                    mResult.setText(response);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
