package com.wxb.jianbao11;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wxb.jianbao11.activity.PublishActivity;
import com.wxb.jianbao11.fragment.GoodsFragment;
import com.wxb.jianbao11.fragment.MineFragment;

public class MainActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    private android.support.v4.app.Fragment contentFragment;
    private RadioButton rb_goods;
    private RadioButton rb_add;
    private RadioButton rb_mine;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        contentFragment = new GoodsFragment();
        transaction.replace(R.id.main_container, contentFragment);
        transaction.commit();
        initEvent();
        rb_add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initEvent() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (checkedId) {
                    case R.id.rb_goods:
                        initGoods();
                        contentFragment = new GoodsFragment();
                        transaction.replace(R.id.main_container, contentFragment);
                        break;
                    case R.id.rb_mine:
                        initMine();
                        contentFragment = new MineFragment();
                        transaction.replace(R.id.main_container, contentFragment);
                        break;
                    default:
                        break;
                }
                transaction.commit();
            }
        });
    }

    private void initView() {
        rg = (RadioGroup) findViewById(R.id.id_rg);
        rb_goods = (RadioButton) findViewById(R.id.rb_goods);
        rb_add = (RadioButton) findViewById(R.id.rb_add);
        rb_mine = (RadioButton) findViewById(R.id.rb_mine);
        fragmentManager = getSupportFragmentManager();
    }

    private void initGoods() {
        rb_goods.setTextColor(Color.parseColor("#000000"));
        rb_mine.setTextColor(Color.parseColor("#a9b7b7"));
    }

    private void initMine() {
        rb_goods.setTextColor(Color.parseColor("#a9b7b7"));
        rb_mine.setTextColor(Color.parseColor("#000000"));
    }



}
