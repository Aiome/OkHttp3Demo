package com.example.aiome.okhttp3demo;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aiome on 2017/1/18.
 */

public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil";
    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpUtil() {
        mOkHttpClient = new OkHttpClient();

    }
    private void log(String msg){
        Log.d(TAG, "log: " + msg);
    }

    //单例模式,获取OkHttpUtil实例,减少系统资源开销
    public static OkHttpUtil getInstance() {
        if (mInstance == null){
            //线程安全
            synchronized (OkHttpUtil.class){
                mInstance = new OkHttpUtil();
            }
        }
        return mInstance;
    }

    /**
     * 同步Get
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException
     */
    private Response getAsync(String url) throws IOException {
        final Request requst = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(requst);
        Response response = call.execute();
        return response;
    }

    /**
     * 同步Get
     * @param url 请求地址
     * @return 字符串
     * @throws IOException
     */
    private String getAsyncString(String url) throws IOException {
        return getAsync(url).body().string();
    }

    /**
     * 同步Get
     * @param url 请求地址
     * @return 流
     * @throws IOException
     */
    private InputStream getAsyncStream(String url) throws IOException {

        return getAsync(url).body().byteStream();
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response post(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    private Request buildPostRequest(String url,Param []params){
//        FormBody formBody = new FormBody.Builder()
//                .build();
//        for (Param param:params){
//            formBody.
//        }
//        RequestBody request = new Request.Builder()
//
        return null;
    }

    public static class Param{
        String key;
        String value;
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
