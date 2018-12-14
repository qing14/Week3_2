package asus.com.bwie.week3_2.presenter;



import java.util.Map;

import asus.com.bwie.week3_2.callback.MyCallBack;
import asus.com.bwie.week3_2.model.Imodelmpl;
import asus.com.bwie.week3_2.view.Iview;

public class IpresenterImpl implements Ipresenter{

    private Iview iview;
    private Imodelmpl imodelmpl;

    public IpresenterImpl(Iview iview) {
        this.iview=iview;
        imodelmpl=new Imodelmpl();
    }

    @Override
    public void startReuqest(String string, Map<String, String> map, Class clas) {
        imodelmpl.startRequestData(string, map, clas, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });
    }
}
