package com.wxb.jianbao11.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.bean.CheckPublished;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.ImageTools;

import java.util.ArrayList;
/*
 * Created by Administrator on 2016/11/30.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter <MyRecyclerAdapter.MyViewHolder> {
    private static final String TAG = "MyRecyclerAdapter";
    Context context;
    ArrayList list;
    private LayoutInflater inflater;
    public OnItemClickListener listener;
    private MyViewHolder holder;

    public MyRecyclerAdapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_all_mine,parent,false);
        holder = new MyViewHolder(view);
        return holder;
    }
    public interface OnItemClickListener{
        void ItemClickListener(View view, int position);
    }
    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Object obj = list.get(position);
        if (!(obj instanceof CheckPublished.DataBean.ListBean)){
            return;
        }
        CheckPublished.DataBean.ListBean checkPublished=(CheckPublished.DataBean.ListBean)obj;
        // 图片
        String image = Contant.IMGQZ+checkPublished.getImage();
        System.out.println("guaju"+image);
        // 讲获取到的数据放到相应的控件上
        holder.tv_money.setText("¥ "+checkPublished.getPrice());
        holder.tv_title.setText(checkPublished.getTitle());
        holder.tv_time.setText(checkPublished.getIssue_time());
        holder.picture.setImageURI(image);
        ImageTools.load(Uri.parse(image),holder.picture,120,120);
        switch (checkPublished.getState()){
            case 0:
                holder.tv_state.setText("发布");
                break;
            case 1:
                holder.tv_state.setText("正常");
                break;
            case 3:
                holder.tv_state.setText("未过审");
                break;
            case 9:
                holder.tv_state.setText("已下架");
                break;
        }

        if (listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 得到当前item的位置
                    int pos = holder.getLayoutPosition();
                    // 把事件交给我们实现的接口那里处理
                    listener.ItemClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView picture;
        TextView tv_title,tv_money,tv_time,tv_state;

        public MyViewHolder(View itemView) {
            super(itemView);
            picture = (SimpleDraweeView) itemView.findViewById(R.id.item_mine_picture);
            tv_title = (TextView) itemView.findViewById(R.id.item_mine_tv_title);
            tv_money = (TextView) itemView.findViewById(R.id.item_mine_tv_money);
            tv_time = (TextView) itemView.findViewById(R.id.item_mine_tv_time);
            tv_state = (TextView) itemView.findViewById(R.id.item_mine_tv_state);
        }
    }
}
