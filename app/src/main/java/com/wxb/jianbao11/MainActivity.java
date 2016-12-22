package com.wxb.jianbao11;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.wxb.jianbao11.fragment.GoodsFragment;
import com.wxb.jianbao11.fragment.MineFragment;
import com.wxb.jianbao11.utils.TimeUtils;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView rb_goods, rb_add, rb_mine;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    initMine();
                    fragmentTransaction.replace(R.id.main_container, new MineFragment());
                }
                break;
        }
        fragmentTransaction.commit();
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
