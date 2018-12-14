package asus.com.bwie.week3_2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.week3_2.adapter.GridAdapter;
import asus.com.bwie.week3_2.adapter.LinearAdapter;
import asus.com.bwie.week3_2.model.Products;
import asus.com.bwie.week3_2.presenter.IpresenterImpl;
import asus.com.bwie.week3_2.view.Iview;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Iview {

    private XRecyclerView recyclerView;
    private IpresenterImpl ipresenter;
    private LinearAdapter linearAdapter;
    private String url="http://www.zhaoapi.cn/product/searchProducts";
    private int mpage;
    private int mSpanCount=2;
    private Map<String, String> map;
    private boolean isLinear=true;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private Button price,synthesize,sales;
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = new HashMap<>();
        mpage=1;
        initView();
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mpage=1;
                requestData();
            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        });
        isLayoutManager();

        linearAdapter.setOnClickListenter(new LinearAdapter.ClickListenter() {
            @Override
            public void onClick(int position) {
                int pid = linearAdapter.getPid(position);
                Intent intent=new Intent(MainActivity.this,TwoActivity.class);
                intent.putExtra("pid",pid+"");
                startActivity(intent);
            }
        });
    }

    private void initView() {
        recyclerView=findViewById(R.id.recycler_linear);
        findViewById(R.id.change).setOnClickListener(this);
        findViewById(R.id.synthesize).setOnClickListener(this);
        findViewById(R.id.sales).setOnClickListener(this);
        findViewById(R.id.price).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
        price=findViewById(R.id.price);
        synthesize=findViewById(R.id.synthesize);
        sales=findViewById(R.id.sales);
        ipresenter=new IpresenterImpl(this);

    }

    public void requestData(){
        map.clear();
        map.put("keywords","手机");
        map.put("page",mpage+"");
        ipresenter.startReuqest(url,map,Products.class);
    }

    public void requestData(int i){
        map.clear();
        map.put("keywords","手机");
        map.put("page",mpage+"");
        map.put("sort",i+"");
        ipresenter.startReuqest(url,map,Products.class);
    }

    public void isLayoutManager(){
        if(isLinear){
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            findViewById(R.id.change).setBackgroundResource(R.drawable.grid);
        }else{
            gridLayoutManager = new GridLayoutManager(this,mSpanCount);
            recyclerView.setLayoutManager(gridLayoutManager);
            findViewById(R.id.change).setBackgroundResource(R.drawable.linear);
        }
        linearAdapter=new LinearAdapter(this,isLinear);
        recyclerView.setAdapter(linearAdapter);
        isLinear=!isLinear;
        mpage=1;
        requestData();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.change:
                isLayoutManager();
                break;
            case R.id.synthesize:
                synthesize.setTextColor(Color.RED);
                sales.setTextColor(Color.BLACK);
                price.setTextColor(Color.BLACK);
                mpage = 1;
                requestData(0);
                break;
            case R.id.sales:
                synthesize.setTextColor(Color.BLACK);
                sales.setTextColor(Color.RED);
                price.setTextColor(Color.BLACK);
                mpage = 1;
                requestData(1);
                break;
            case R.id.price:
                synthesize.setTextColor(Color.BLACK);
                sales.setTextColor(Color.BLACK);
                price.setTextColor(Color.RED);
                mpage = 1;
                requestData(2);
                break;
            default:break;
        }
    }

    @Override
    public void onSuccessData(Object data) {
        if(data instanceof Products){
            Products products= (Products) data;
            if(mpage==1){
                linearAdapter.setMlist(products.getData());
            }else{
                linearAdapter.addMlist(products.getData());
            }
        }
        mpage++;
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    @Override
    public void onFailData(Exception e) {
        Log.i("TAG",e.getLocalizedMessage());
    }
}
