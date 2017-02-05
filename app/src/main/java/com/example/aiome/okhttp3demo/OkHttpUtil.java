package com.example.aiome.okhttp3demo;

import android.util.Log;

import com.google.gson.internal.$Gson$Types;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Aiome on 2017/1/18.
 */

public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil-";
    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;
    private boolean log = true;

    private OkHttpUtil() {
        mOkHttpClient = new OkHttpClient();

    }
    private void log(String msg){
        if (log){
            Log.d(TAG, msg);
        }
    }
    private void logUrl(String msg){
        log("Url:" + msg);
    }
    private void logResponse(String msg){
        log("Response:" + msg);
    }

    /**
     * 设置打印log,默认开启
     * @param b
     */
    public void isLog(boolean b){
        log = b;
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

    /**
     * 同步Get
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException
     */
    public Response getSync(String url) throws IOException {
        logUrl(url);

        final Request requst = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(requst);
        Response response = call.execute();

        logResponse(response.toString());
        return response;
    }

    /**
     * 同步Get
     * @param url 请求地址
     * @return 字符串
     * @throws IOException
     */
    public String getSyncString(String url) throws IOException {
        logUrl(url);

        String response = getSync(url).body().string();

        logResponse(response);
        return response;
    }

    /**
     * 同步Get
     * @param url 请求地址
     * @return 流
     * @throws IOException
     */
    public InputStream getSyncStream(String url) throws IOException {
        logUrl(url);

        return getSync(url).body().byteStream();
    }

    /**
     * 同步Post
     * @param url 请求地址
     * @param params post的参数
     * @return 请求结果
     */
    public Response postSync(String url, Param... params) throws IOException {
        logUrl(url);

        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());

        return response;
    }

    /**
     * 同步Post
     * @param url 请求地址
     * @param params post的参数 可以为null
     * @return 字符串
     * @throws IOException
     */
    public String postSyncString(String url, Param... params) throws IOException {
        logUrl(url);

        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());

        return response.body().string();
    }

    /**
     * 同步Post
     * @param url 请求地址
     * @param params post的参数 可以为null
     * @return
     * @throws IOException
     */
    public InputStream postSyncStream(String url, Param... params) throws IOException {
        logUrl(url);

        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());

        return response.body().byteStream();
    }

    /**
     * 同步Post上传多个文件
     * @param url 请求地址
     * @param files 文件
     * @param fileKey 文件关键字
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public Response postSyncFile(String url, File []files, String []fileKey, Param... params) throws IOException {
        logUrl(url);
        Request request = bulidMultipartRequest(url,files,fileKey,params);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());

        return response;
    }

    /**
     * 同步Post上传多个文件,不带参数
     * @param url
     * @param files
     * @param fileKey
     * @return
     * @throws IOException
     */
    public Response postSyncFile(String url, File []files, String []fileKey) throws IOException {
        logUrl(url);

        Request request = bulidMultipartRequest(url,files,fileKey,null);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());
        return response;
    }

    /**
     * 同步Post上传单个文件
     * @param url 请求地址
     * @param file 文件
     * @param fileKey 文件关键字
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public Response postSyncFile(String url, File file, String []fileKey, Param... params) throws IOException {
        logUrl(url);

        Request request = bulidMultipartRequest(url,new File[]{file},fileKey,params);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());

        return response;
    }

    /**
     * 同步Post上传单个文件，不带参数
     * @param url 请求地址
     * @param file 文件
     * @param fileKey 文件关键字
     * @return
     * @throws IOException
     */
    public Response postSyncFile(String url, File file, String []fileKey) throws IOException {
        logUrl(url);

        Request request = bulidMultipartRequest(url,new File[]{file},fileKey,null);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());

        return response;
    }

    public void getAsync(String url,final ResultCallBack callBack){
        final Request request = new Request.Builder()
                .url(url)
                .build();
        handleResult(request,callBack);
    }

    private void handleResult(Request request, ResultCallBack back) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    private Request bulidMultipartRequest(String url, File []files, String []fileKey, Param[] params) {
        if (params == null){
            params = new Param[0];
        }

        //防止filekey缺少,若fileKey少于file则以文件名作为fileKey
        if (files.length > fileKey.length) {
            fileKey = new String[files.length];
            for (int i = 0; i < files.length; i++){
                fileKey[i] = files[i].getName();
            }
        }

        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Param param:params){
            multipartBody.addFormDataPart(param.key,param.value);
            log("Param: " + param.key + " - " + param.value);
        }

        if (files != null){
            for (int i = 0; i < files.length; i++){
                multipartBody.addFormDataPart(fileKey[i],files[i].getName(),fileBody(files[i]));
                log("Files: " + fileKey[i] + " - " + files[i].getName());
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(multipartBody.build())
                .build();

        return request;
    }



    private RequestBody fileBody(File file) {
        return RequestBody.create(MediaType.parse("application/octet-stream"),file);
    }

    private Request buildPostRequest(String url,Param []params){
        if (params == null){
            params = new Param[0];
        }

        FormBody.Builder formBody = new FormBody.Builder();
        for (Param param:params){
            formBody.add(param.key,param.value);
            log("Param: " + param.key + " - " + param.value);
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        return request;
    }

    public static abstract class ResultCallBack<T>{
        Type mType;
        public ResultCallBack() {
            mType = getSuperclassTypeParameter(getClass());
        }

        Type getSuperclassTypeParameter(Class<?> subClass){
            Type superclass = subClass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }






}
