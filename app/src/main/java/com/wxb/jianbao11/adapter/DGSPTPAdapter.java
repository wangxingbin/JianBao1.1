package com.wxb.jianbao11.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.ImageTools;

import java.util.ArrayList;


/**
 * Created by ti on 2016/12/7.
 */

public class DGSPTPAdapter extends RecyclerView.Adapter<DGSPTPAdapter.MyViewHolder> {

    ArrayList<String> list;
    public Context context;
    public LayoutInflater inflater;
    private View v;
    private MyViewHolder myViewHolder;

    public DGSPTPAdapter(ArrayList<String> list, Context context){
        this.context=context;
        this.list=list;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = inflater.inflate(R.layout.dgsptp_rv_item,parent,false);
        myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Uri uri = Uri.parse(Contant.IMGQZ+list.get(position));
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)//设置自动播放，如果不设置，没有动画
                .build();

        holder.id_sdv.setController(controller);
        ImageTools.load(uri,holder.id_sdv,300,300);

        if(mListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos=holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemClickListener(v,pos);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public DGSPTPAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void ItemClickListener(View view, int postion);

    }

    public void setOnClickListener(DGSPTPAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView id_sdv;

        public MyViewHolder(View v) {
            super(v);
            id_sdv= (SimpleDraweeView) v.findViewById(R.id.id_sdv);

        }
    }

}
