package asus.com.bwie.week3_2.okhttp;

import android.os.Handler;
import android.os.Looper;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import asus.com.bwie.week3_2.callback.MyCallBack;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private OkHttpClient mClient;
    private static OkHttpUtils okHttpUtils;
    private Handler handler=new Handler(Looper.getMainLooper());

    public static OkHttpUtils getIntance(){
        if(okHttpUtils==null){
            synchronized (OkHttpUtils.class){
                okHttpUtils=new OkHttpUtils();
            }
        }
        return okHttpUtils;
    }

    public OkHttpUtils() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient=new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }


    public void postEneuque(String string, Map<String,String> map, final Class clas, final MyCallBack myCallBack){
        FormBody.Builder builder=new FormBody.Builder();
        for(Map.Entry<String,String> entry:map.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .url(string)
                .post(body)
                .build();

        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final Object o = new Gson().fromJson(json, clas);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onSuccess(o);
                    }
                });
            }
        });
    }
}
