package asus.com.bwie.week3_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asus.com.bwie.week3_2.model.Shops;
import asus.com.bwie.week3_2.presenter.IpresenterImpl;
import asus.com.bwie.week3_2.view.Iview;

public class TwoActivity extends AppCompatActivity implements Iview {

    private int pid;
    private Banner banner;
    private TextView title,price,barginprice;
    private IpresenterImpl ipresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        banner = findViewById(R.id.banner);
        title=findViewById(R.id.title);
        price=findViewById(R.id.price);
        ipresenter= new IpresenterImpl(this);
        barginprice=findViewById(R.id.barginprice);
        Intent intent=getIntent();
        final String pids = intent.getStringExtra("pid");
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        Map<String,String> map=new HashMap<>();
        map.put("pid",pids);
        ipresenter.startReuqest("http://www.zhaoapi.cn/product/getProductDetail",map,Shops.class);
    }

    @Override
    public void onSuccessData(Object data) {
        List<String> list=new ArrayList<>();
        Shops goods= (Shops) data;
        String[] split = goods.getData().getImages().split("\\|");
        for(int i=0;i<split.length;i++){
            list.add(split[i]);
        }
        banner.setImages(list);
        banner.start();
        barginprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        price.setText("¥"+goods.getData().getPrice()+"");
        title.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        title.setText(goods.getData().getTitle());
        barginprice.setText("¥"+goods.getData().getBargainPrice()+"");
    }

    @Override
    public void onFailData(Exception e) {

    }
}
