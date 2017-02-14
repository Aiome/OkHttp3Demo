package com.example.aiome.okhttp3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.aiome.okhttp3demo.OkHttpUtil.ResultCallback;

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
    private Button mUploadFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        findView();

        mUploadFiles.setOnClickListener(mOnClickListener);
        mUploadFile.setOnClickListener(mOnClickListener);
        mUploadFilesWithParam.setOnClickListener(mOnClickListener);
        mUploadFileWithParam.setOnClickListener(mOnClickListener);

    }

    private void findView() {
        mUploadFile = (Button) findViewById(R.id.uploadFile);
        mUploadFiles = (Button) findViewById(R.id.uploadFiles);
        mUploadFilesWithParam = (Button) findViewById(R.id.uploadFilesWithParam);
        mUploadFileWithParam = (Button) findViewById(R.id.uploadFileWithParam);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
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

    private void uploadFileWithParam() {
    }

    private void uploadFilesWithParam() {
    }

    private void uploadFiles() {
    }

    private void uploadFile() {
        File file = new File("/storage/emulated/0/Download/1.jpg");
        try {
            OkHttpUtil.getInstance().postAsyncFile("http://192.168.81.2/api/fs/upload", file, "测试", new ResultCallback<String>() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(String response) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
