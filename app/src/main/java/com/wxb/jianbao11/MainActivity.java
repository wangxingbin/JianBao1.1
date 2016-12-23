package com.wxb.jianbao11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.wxb.jianbao11.activity.Login;
import com.wxb.jianbao11.activity.PublishActivity;
import com.wxb.jianbao11.fragment.GoodsFragment;
import com.wxb.jianbao11.fragment.MineFragment;
import com.wxb.jianbao11.utils.TimeUtils;


public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private ImageView rb_goods,rb_add,rb_mine;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SharedPreferences sp = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
    }

    private void initView() {
        rb_goods = (ImageView) findViewById(R.id.rb_goods);
        rb_add = (ImageView) findViewById(R.id.rb_add);
        rb_mine = (ImageView) findViewById(R.id.rb_mine);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        /*
        默认页面:GoodsFragment(商品)
         */
        fragmentTransaction.replace(R.id.main_container, new GoodsFragment());
        fragmentTransaction.commit();

        rb_goods.setOnClickListener(this);
        rb_add.setOnClickListener(this);
        rb_mine.setOnClickListener(this);
    }

    public void onClick(View v) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.rb_goods:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    initGoods();
                    fragmentTransaction.replace(R.id.main_container, new GoodsFragment());
                }
                break;
            case R.id.rb_mine:
                initMine();
                if (token == null || token.isEmpty() ){
                    startActivity(new Intent(MainActivity.this, Login.class));
                }else {
                    fragmentTransaction.replace(R.id.main_container,new MineFragment());
                }

                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    initMine();
                    fragmentTransaction.replace(R.id.main_container, new MineFragment());
                }
                break;
        }
        fragmentTransaction.commit();

        rb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initGoods() {
        rb_goods.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_home, null));
        rb_mine.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_person_selected, null));
    }

    private void initMine() {
        rb_goods.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_home_selected, null));
        rb_mine.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_person, null));
    }
}
