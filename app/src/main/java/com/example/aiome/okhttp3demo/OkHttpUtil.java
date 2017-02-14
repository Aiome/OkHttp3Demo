package com.example.aiome.okhttp3demo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

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

/**
 * Created by Aiome on 2017/1/18.
 */

public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil-";
    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;
    private boolean log = true;

    private OkHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
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
        Request request = buildMultipartRequest(url,files,fileKey,params);
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

        Request request = buildMultipartRequest(url,files,fileKey,null);
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

        Request request = buildMultipartRequest(url,new File[]{file},fileKey,params);
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

        Request request = buildMultipartRequest(url,new File[]{file},fileKey,null);
        Response response = mOkHttpClient.newCall(request).execute();

        logResponse(response.toString());

        return response;
    }

    /**
     * 异步get请求
     * @param url 请求地址
     * @param callBack 回调
     */
    public void getAsync(String url,final ResultCallback callBack){
        final Request request = new Request.Builder()
                .url(url)
                .build();
        handleResult(request,callBack);
    }

    /**
     * 异步post请求
     * @param url 请求地址
     * @param callback 回调
     * @param params 请求参数
     */
    public void postAsync(String url, final ResultCallback callback, Param... params){
        Request request = buildPostRequest(url, params);
        handleResult(request, callback);
    }

    /**
     * 异步post请求
     * @param url 请求地址
     * @param callback 回调
     * @param paramMap 请求参数
     */
    public void postAsync(String url, Map<String, String> paramMap, final ResultCallback callback){
        Param[] params = mapToParams(paramMap);
        Request request = buildPostRequest(url, params);
        handleResult(request, callback);
    }

    /**
     * 异步post上传文件
     * @param url 请求地址
     * @param files 文件
     * @param fileKey 文件关键字
     * @param callback 回调
     * @param params 参数
     * @throws IOException
     */
    public void postAsyncFile(String url, File []files, String []fileKey, ResultCallback callback, Param... params) throws IOException {
        logUrl(url);
        Request request = buildMultipartRequest(url,files,fileKey,params);

        handleResult(request, callback);
    }

    /**
     * 异步post上传文件不带参数
     * @param url 请求地址
     * @param files  文件
     * @param fileKey 文件关键字
     * @param callback 回调
     * @throws IOException
     */
    public void postAsyncFile(String url, File []files, String []fileKey, ResultCallback callback) throws IOException {
        logUrl(url);
        Request request = buildMultipartRequest(url,files,fileKey,null);

        handleResult(request, callback);
    }

    /**
     * 异步post的文件上传，单文件且携带其他form参数上传
     * @param url 请求地址
     * @param file 文件
     * @param fileKey 文件关键字
     * @param callback 回调
     * @param params 参数
     * @throws IOException
     */
    void postAsyncFile(String url, File file, String fileKey, ResultCallback callback, Param... params) throws IOException {
        logUrl(url);
        Request request = buildMultipartRequest(url, new File[]{file}, new String[]{fileKey}, params);

        handleResult(request, callback);
    }
    /**
     * 异步post的文件上传，单文件不带参数
     * @param url 请求地址
     * @param file 文件
     * @param fileKey 文件关键字
     * @param callback 回调
     * @throws IOException
     */

    private void postAsyncFile(String url, File file, String fileKey,ResultCallback callback) throws IOException {
        logUrl(url);
        Request request = buildMultipartRequest(url, new File[]{file}, new String[]{fileKey}, null);

        handleResult(request, callback);
    }

    private Param[] mapToParams(Map<String, String> map) {
        if (map == null){
            return new Param[0];
        }
        Param[] params = new Param[map.size()];
        int i = 0;
        for (Map.Entry<String,String> entry : map.entrySet()){
            params[i++] = new Param(entry.getKey(),entry.getValue());
        }
        return params;
    }

    private void handleResult(Request request, final ResultCallback callBack) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendOnFailureCallback(call, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    final String string = response.body().string();
                    if (callBack.mType == String.class) {
                        sendOnResponseCallback(string, callBack);
                    } else {
                        Object o = mGson.fromJson(string, callBack.mType);
                        sendOnResponseCallback(o, callBack);
                    }
                }catch (com.google.gson.JsonParseException e){
                    //json解析异常
                    sendOnFailureCallback(call, e, callBack);
                }catch (IOException e){
                    sendOnFailureCallback(call, e, callBack);
                }
            }
        });
    }

    private void sendOnResponseCallback(final Object response, final ResultCallback callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.onResponse(response);
                }
            }
        });
    }


    private void sendOnFailureCallback(final Call call, final Exception e, final ResultCallback callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.onError(call,e);
                }
            }
        });
    }


    private Request buildMultipartRequest(String url, File []files, String []fileKey, Param[] params) {
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
            multipartBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\"")
                    ,RequestBody.create(null, param.value));
            log("Param: " + param.key + " - " + param.value);
        }

        if (files != null){
            for (int i = 0; i < files.length; i++){
                multipartBody.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKey[i] + "\"; filename=\"" + files[i].getName() + "\"")
                        ,fileBody(files[i]));
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
        //获取文件MIME
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(file.getName());
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        //返回请求体
        return RequestBody.create(MediaType.parse(contentTypeFor),file);
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

    public static abstract class ResultCallback<T>{
        Type mType;
        public ResultCallback () {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subClass){
            Type superclass = subClass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
        public abstract void onError(Call call, Exception e);

        public abstract void onResponse(T response);
    }






}
