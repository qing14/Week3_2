package asus.com.bwie.week3_2.model;


import java.util.Map;

import asus.com.bwie.week3_2.callback.MyCallBack;

public interface Imodel {
    void startRequestData(String string, Map<String, String> map, Class clas, MyCallBack myCallBack);
}
