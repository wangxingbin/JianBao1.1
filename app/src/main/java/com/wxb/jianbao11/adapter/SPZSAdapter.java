package com.wxb.jianbao11.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.bean.LBZSbean;
import com.wxb.jianbao11.utils.ImageTools;

import java.util.ArrayList;

/**
 * Created by ti on 2016/12/21.
 */

public class SPZSAdapter extends RecyclerView.Adapter<SPZSAdapter.MyViewHolder> {

    public Context context;
    public LayoutInflater inflater;
    public ArrayList<LBZSbean.DataBean.ListBean> list;
    private View v;
    private MyViewHolder myViewHolder;


    public SPZSAdapter(Context context, ArrayList<LBZSbean.DataBean.ListBean> list) {

        this.context = context;
        this.list = list;

        inflater = LayoutInflater.from(context);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v = inflater.inflate(R.layout.item_list_lbzs, parent, false);
        myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        LBZSbean.DataBean.ListBean lb = list.get(position);

        Uri uri = Uri.parse("http://192.168.4.188/Goods/uploads/" + lb.getImage());

        holder.title.setText(lb.getTitle());
        ImageTools.load(uri,holder.sdv,120,120);
        holder.time.setText(lb.getIssue_time());
        holder.price.setText("¥ "+lb.getPrice() + "");
        if(lb.getState()==1){
            holder.state.setText("正常");

        }else if(lb.getState()==0){
            holder.state.setText("发布");

        }else if(lb.getState()==9){
            holder.state.setText("已下架");

        }
        else if(lb.getState()==3){
            holder.state.setText("未审核");

        }


        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemClickListener(v, pos);

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public OnItemClickListener mListener;

    public interface OnItemClickListener {
        void ItemClickListener(View view, int postion);

    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView price,title,time,gzsl,state;
        SimpleDraweeView sdv;
        ImageView guanzhu;

        public MyViewHolder(View v) {
            super(v);
            sdv= (SimpleDraweeView) v.findViewById(R.id.id_sdv);
            price= (TextView) v.findViewById(R.id.tv_fbprice);
            title= (TextView) v.findViewById(R.id.tv_fbtitle);
            time= (TextView) v.findViewById(R.id.tv_fbtime);
            gzsl= (TextView) v.findViewById(R.id.tv_gzsl);
            state= (TextView) v.findViewById(R.id.tv_state);
            guanzhu= (ImageView) v.findViewById(R.id.iv_guanzhu);

        }
    }

}

