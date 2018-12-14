package asus.com.bwie.week3_2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.week3_2.R;
import asus.com.bwie.week3_2.model.Products;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Products.DataBean> mlist;
    private Context context;

    public GridAdapter(Context context) {
        this.context = context;
        mlist=new ArrayList<>();
    }

    public void setMlist(List<Products.DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }
    public void addMlist(List<Products.DataBean> mlist) {
        this.mlist.addAll(mlist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycler_grid,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        Products.DataBean bean=mlist.get(i);
        String[] split = bean.getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.imageView);
        holder.title.setText(bean.getTitle());
        holder.price.setText("¥"+bean.getPrice());
        holder.salenum.setText("已售"+bean.getSalenum()+"件");
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;
        private TextView price;
        private TextView salenum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.linear_image);
            title=itemView.findViewById(R.id.linear_title);
            price=itemView.findViewById(R.id.linear_bargainPrice);
            salenum=itemView.findViewById(R.id.linear_salenum);
            TextPaint paint = title.getPaint();
            paint.setFakeBoldText(true);
        }
    }
}
