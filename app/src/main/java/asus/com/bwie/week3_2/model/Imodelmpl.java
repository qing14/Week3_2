package asus.com.bwie.week3_2.model;



import java.util.Map;

import asus.com.bwie.week3_2.callback.MyCallBack;
import asus.com.bwie.week3_2.okhttp.OkHttpUtils;

public class Imodelmpl implements Imodel{
    @Override
    public void startRequestData(String string, Map<String, String> map, Class clas, final MyCallBack myCallBack) {
        OkHttpUtils.getIntance().postEneuque(string, map, clas, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                myCallBack.onSuccess(data);
            }

            @Override
            public void onFail(Exception e) {
                myCallBack.onFail(e);
            }
        });
    }
}
