package com.wxb.jianbao11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wxb.jianbao11.activity.Login;
import com.wxb.jianbao11.activity.PublishActivity;
import com.wxb.jianbao11.fragment.GoodsFragment;
import com.wxb.jianbao11.fragment.MineFragment;
import com.wxb.jianbao11.utils.ShowToastUtils;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private ImageView rb_goods,rb_add,rb_mine;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String token;
    private String info;
    public GoodsFragment goodsFragment;
    public MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        info = intent.getStringExtra("info");

        initView();
        SharedPreferences sp = getSharedPreferences("TOKEN", MODE_PRIVATE);
        token = sp.getString("token", "");
    }

    private void initView() {
        rb_goods = (ImageView) findViewById(R.id.rb_goods);
        rb_add = (ImageView) findViewById(R.id.rb_add);
        rb_mine = (ImageView) findViewById(R.id.rb_mine);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        rb_goods.setOnClickListener(this);
        rb_add.setOnClickListener(this);
        rb_mine.setOnClickListener(this);

         /*
        默认页面:GoodsFragment(商品)
         */
        goodsFragment = new GoodsFragment();
        fragmentTransaction.add(R.id.main_container,goodsFragment);
        fragmentTransaction.show(goodsFragment);
        fragmentTransaction.commit();
        initGoods();

    }

    @Override
    public void onClick(View v) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.rb_goods:
                initGoods();
                if (goodsFragment ==null) {
                    goodsFragment = new GoodsFragment();
                    fragmentTransaction.add(R.id.main_container, goodsFragment);
                }
                if (mineFragment!=null){
                    fragmentTransaction.hide(mineFragment);
                }
                fragmentTransaction.show(goodsFragment);
                break;
            case R.id.rb_mine:
                SharedPreferences token =  getSharedPreferences("TOKEN", MODE_PRIVATE);
                boolean isLogin = token.getBoolean("isLogin", false);
                if (isLogin){
                    initMine();
                    if (mineFragment==null) {
                        mineFragment = new MineFragment();
                        fragmentTransaction.add(R.id.main_container, mineFragment);
                    }
                    if (goodsFragment !=null){
                        fragmentTransaction.hide(goodsFragment);
                    }
                    fragmentTransaction.show(mineFragment);
                }
                else{
                    Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            case R.id.rb_add:
                SharedPreferences token2 =  getSharedPreferences("TOKEN", MODE_PRIVATE);
                boolean isLogin2 = token2.getBoolean("isLogin", false);
                if (isLogin2){
                    startActivity(new Intent(MainActivity.this,PublishActivity.class));
                }
                else{
                    ShowToastUtils.showToast(MainActivity.this,"请登录");
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                }


                break;
        }
        fragmentTransaction.commit();


    }
    private void initGoods() {
        rb_goods.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_home_selected,null));
        rb_mine.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_person,null));
        rb_add.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_post_selected,null));
    }
    private void initMine(){
        rb_goods.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_home,null));
        rb_add.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_post_selected,null));
        rb_mine.setImageDrawable(getResources().getDrawable(R.drawable.comui_tab_person_selected,null));
    }
    private static boolean mBackKeyPressed = false;//记录是否有首次按键
    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mBackKeyPressed = true;
                    new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                    @Override
                        public void run() {
                    mBackKeyPressed = false;
                    }
                }, 2000);
            }
        else{
            //退出程序
                this.finish();
            // System.exit(0);
            }
    }
}
