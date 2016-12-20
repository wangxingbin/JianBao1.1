package com.wxb.jianbao11.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.bean.LBZSbean;

import java.util.ArrayList;

/**
 * Created by ti on 2016/12/20.
 */

public class SPZSAdapter extends BaseAdapter {

    Context context;
    ArrayList<LBZSbean.DataBean.ListBean> list;
    LayoutInflater inflater;

    public SPZSAdapter(Context context,ArrayList<LBZSbean.DataBean.ListBean> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view==null){
            view=inflater.inflate(R.layout.item_list_lbzs,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }
        vh= (ViewHolder) view.getTag();

        Uri uri = Uri.parse("http://192.168.4.188/Goods/uploads/"+list.get(i).getImage());
        vh.sdv.setImageURI(uri);

        vh.price.setText(list.get(i).getPrice());
        vh.title.setText(list.get(i).getTitle());
        vh.time.setText(list.get(i).getIssue_time());

        return view;
    }

    class ViewHolder{
        TextView price,title,time;
        SimpleDraweeView sdv;

        public ViewHolder(View v){
            sdv= (SimpleDraweeView) v.findViewById(R.id.id_sdv);
            price= (TextView) v.findViewById(R.id.tv_fbprice);
            title= (TextView) v.findViewById(R.id.tv_fbtitle);
            time= (TextView) v.findViewById(R.id.tv_fbtime);

        }
    }
}
