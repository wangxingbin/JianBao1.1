package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxb.jianbao11.R;
import com.wxb.jianbao11.bean.publish_Bean;
import com.wxb.jianbao11.utils.ImageTools;
import com.wxb.jianbao11.utils.MyOkhttp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by big_cow on 2016/11/30.
 * 主要实现发布功能
 * 必选标题，描述，价格，电话
 * 图片和qq微信email选填
 */

public class PublishActivity extends Activity implements View.OnClickListener {
    // 图片的数量
    private static final int IMAGE_SIZE = 5;

    private static final int TAKE_PICTURE = 0;
    private static final int CHOOSE_PICTURE = 1;

    // 查找EditText ids
    private EditText et_jianbao_biaoti, et_jianbao_miaoshu, et_jianbao_number, et_jianbao_price, et_jianbao_email;
    // EditText.toString得到字符串
    private String str_jianbao_biaoti, str_jianbao_miaoshu, str_jianbao_number,
            str_jianbao_price, str_jianbao_email;
    //  删除EditText的叉号 ids
    private ImageView im_et_cha, im_et_cha2, im_et_cha3, im_et_cha4, im_et_cha5;
    // 发布按钮ids
    private TextView tv_jianbao_fabu;
    private HashMap<String, String> map;
    // TODO 这个还没有实现
    // private ArrayList<String> imgs;

    //TODO 这个是实现 相册 相机的
    // 写一个存放照片的集合
    ArrayList<ImageView> pictureList = new ArrayList<>();
    // 存放删除照片的集合
    ArrayList<ImageView> xPictureList = new ArrayList<>();
    private static final int[] imageIds = {R.id.takephoto_picture, R.id.takephoto_picture2,
            R.id.takephoto_picture3, R.id.takephoto_picture4, R.id.takephoto_picture5};
    private static final int[] xImageIds = {R.id.im_x1, R.id.im_x2, R.id.im_x3, R.id.im_x4, R.id.im_x5};

    private int takePhotoName = 0;
    //拍照的路径
    private String takePhotoPath;
    //已经保存的图片数量
    private int idx;
    private Bitmap newBitmap;
    // 存放照片的路径
   private ArrayList<String> mList = new ArrayList<String>();
    // 存放照片的数组
    private ArrayList<Bitmap> mBitmapList = new ArrayList<Bitmap>();
    //照相机的图片视图（负责选择添加照片）
    private ImageView iv_add = null;
    // 图片的路径
    private Uri originalUri;
    private ImageView back;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publishactivity);
        initView();
        initPhoto();
        intitButton();
    }

    //初始化控件
    private void initView() {
        back = (ImageView) findViewById(R.id.bar_iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        text = (TextView) findViewById(R.id.bar_tv_name);
        text.setText("发布商品");

        et_jianbao_biaoti = (EditText) findViewById(R.id.et_jiianbao_biaoti); //标题
        et_jianbao_miaoshu = (EditText) findViewById(R.id.et_jianbao_miaoshu); //描述
        et_jianbao_number = (EditText) findViewById(R.id.et_jianbao_number);//手机号
        et_jianbao_price = (EditText) findViewById(R.id.et_jianbao_price);// 价格;
        et_jianbao_email = (EditText) findViewById(R.id.et_jianbao_email); //社交软件
        tv_jianbao_fabu = (TextView) findViewById(R.id.tv_jianbao_fabu); // 发布按钮
        et_jianbao_biaoti.addTextChangedListener(textWatcher);
        et_jianbao_miaoshu.addTextChangedListener(textWatcher);
        im_et_cha = (ImageView) findViewById(R.id.im_et_x1); // x EditText
        im_et_cha2 = (ImageView) findViewById(R.id.im_et_x4); // x
        im_et_cha3 = (ImageView) findViewById(R.id.im_et_x3); // x
        im_et_cha4 = (ImageView) findViewById(R.id.im_et_x2); // x
        im_et_cha5 = (ImageView) findViewById(R.id.im_et_x5); // x
        im_et_cha.setOnClickListener(PublishActivity.this); // 删除EditText的点击事件
        im_et_cha2.setOnClickListener(PublishActivity.this);
        im_et_cha3.setOnClickListener(PublishActivity.this);
        im_et_cha4.setOnClickListener(PublishActivity.this);
        im_et_cha5.setOnClickListener(PublishActivity.this);

        // 图片的一些事件
        for (int i = 0; i < IMAGE_SIZE; i++) {
            // 图片视图
            pictureList.add((ImageView) findViewById(imageIds[i]));
            // 图片视图绑定点击事件
            pictureList.get(i).setOnClickListener(PublishActivity.this);

            // 叉号图片视图
            xPictureList.add((ImageView) findViewById(xImageIds[i]));
            xPictureList.get(i).setOnClickListener(PublishActivity.this);
        }

        //初始化iv_add
        iv_add = pictureList.get(0);
    }

    // 相机相册的点击事件
    private void initPhoto() {
        pictureList.get(0).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPicturePicker(PublishActivity.this);
            }
        });
    }

    public void showPicturePicker(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("图片来源");
        builder.setNegativeButton("取消", null);
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case TAKE_PICTURE:
                        takePhotoName++;
                        takePhotoPath = Environment.getExternalStorageDirectory() + "/image" + takePhotoName + ".jpg";  //拍照的路径
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri imageUri = Uri.fromFile(new File(takePhotoPath));
                        //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;

                    case CHOOSE_PICTURE:
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;

                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

    /**
     * 获取照片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:

                    //将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(takePhotoPath);
                    newBitmap = ImageTools.zoomLittleBitmap(bitmap);
                    //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();
                    //已经保存的图片数量
                    idx = mBitmapList.size();
                    //TODO
                    pictureList.get(idx).setImageBitmap(newBitmap);
                    pictureList.get(idx).setVisibility(View.VISIBLE); // 图片显示
                    // relativeLayoutList.get(idx).setVisibility(View.VISIBLE); // 整个控件显示
                    xPictureList.get(idx).setVisibility(View.VISIBLE); // x显示
                    addMore();
                    mList.add(takePhotoPath);
                    mBitmapList.add(newBitmap);

                     break;

                case CHOOSE_PICTURE:
                    ContentResolver resolver = getContentResolver();
                    //照片的原始资源地址
                    if (data==null){
                        return;
                    }
                    originalUri = data.getData();
                    try {
                        //使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            newBitmap = ImageTools.zoomLittleBitmap(photo);
                            //释放原始图片占用的内存，防止out of memory异常发生
                            photo.recycle();
                            //照片列表的长度
                            idx = mBitmapList.size();
                            //设置图片
                            pictureList.get(idx).setImageBitmap(newBitmap);
                            //删除点击事件
                            pictureList.get(idx).setOnClickListener(null);
                            // 图片显示
                            pictureList.get(idx).setVisibility(View.VISIBLE);
                            // x显示
                            xPictureList.get(idx).setVisibility(View.VISIBLE);
                            // 整个控件显示
                            //relativeLayoutList.get(idx).setVisibility(View.VISIBLE);
                            //添加图片到列表
                            String s = originalUri.toString();
                            mBitmapList.add(newBitmap);
                            mList.add(s);
                            //检查图片列表是否已满
                            if (mBitmapList.size() < IMAGE_SIZE) {
                                //添加图片控件后移
                                addMore();
                            }
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                default:
                    break;
            }
        }
    }

    // 添加照片
    private void addMore() {
        //添加照片控件后移
        iv_add = pictureList.get(idx + 1);
        //设置图片
        iv_add.setImageResource(R.mipmap.zzq_pppp);
        //设置显示
        iv_add.setVisibility(View.VISIBLE);
        //设置点击事件
        iv_add.setOnClickListener(PublishActivity.this);

    }

    // 移除图片的方法
    private void resetImageView(int position) {

        //删除指定位置的图片
        mBitmapList.remove(position);

        //被删除图片右侧有图片，则左移
        for (int i = position; i < mBitmapList.size(); i++) {
            Bitmap img = mBitmapList.get(i);
            pictureList.get(i).setImageBitmap(img);
        }
        //位移后空出的位置变照相机
        iv_add = pictureList.get(mBitmapList.size());
        iv_add.setImageResource(R.mipmap.zzq_pppp);
        iv_add.setOnClickListener(this);
        //叉号隐藏
        xPictureList.get(mBitmapList.size()).setVisibility(View.INVISIBLE);

        //最后一个图片控件
        if (mBitmapList.size() + 1 < IMAGE_SIZE) {
            pictureList.get(mBitmapList.size() + 1).setImageBitmap(null);
            pictureList.get(mBitmapList.size() + 1).setOnClickListener(null);
        }
    }


    // 点击×  清除EditTextq
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_et_x1:
                et_jianbao_biaoti.setText("");
                break;
            case R.id.im_et_x3:
                et_jianbao_price.setText("");
                break;
            case R.id.im_et_x4:
                et_jianbao_number.setText("");
                break;
            case R.id.im_et_x2:
                et_jianbao_miaoshu.setText("");
                break;
            case R.id.im_et_x5:
                et_jianbao_email.setText("");
                break;
        }
        switch (view.getId()) {
            case R.id.im_x1:
                resetImageView(0);
                break;
            case R.id.im_x2:
                resetImageView(1);
                break;
            case R.id.im_x3:
                resetImageView(2);
                break;
            case R.id.im_x4:
                resetImageView(3);
                break;
            case R.id.im_x5:
                resetImageView(4);
                break;

        }

        ImageView iv = (ImageView) findViewById(view.getId());
        if (iv_add.equals(iv)) {
            showPicturePicker(PublishActivity.this);
        }

    }

    // 上传数据
    private void initUploading() {
        //intiData();
        // OkHttp接口回调
        MyOkhttp.setGetEntiydata(new MyOkhttp.EntiyData() {
            @Override
            public void getEntiy(Object obj) {

                publish_Bean publish_o1 = (publish_Bean) obj;
                final String status = publish_o1.getStatus();
                if (status.equals("200")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PublishActivity.this, "发布失败请登录", Toast.LENGTH_SHORT).show();
                            if ("301".equals(status)){

                                Intent intent = new Intent(PublishActivity.this, Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                PublishActivity.this.startActivity(intent);
                            }


                        }
                    });
                }
            }
        });

        MyOkhttp.postMuti(map, "http://192.168.4.188/Goods/"
                + "app/item/issue.json", PublishActivity.this, publish_Bean.class, mList);
    }

    private void intiData() {
        str_jianbao_biaoti = et_jianbao_biaoti.getText().toString().trim();
        str_jianbao_miaoshu = et_jianbao_miaoshu.getText().toString().trim();
        str_jianbao_price = et_jianbao_price.getText().toString().trim();
        str_jianbao_number = et_jianbao_number.getText().toString().trim();
        str_jianbao_email = et_jianbao_email.getText().toString().trim();
        map = new HashMap<>();
        map.put("title", str_jianbao_biaoti);
        map.put("description", str_jianbao_miaoshu);
        map.put("price", str_jianbao_price);
        map.put("mobile", str_jianbao_number);
        map.put("email", str_jianbao_email);
       /* SharedPreferences share = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = share.getString("token", "ll");*/
        map.put("token", "3650BB5FD2234C688E2AC9EC1077928D");

    }

    private void intitButton() { //点击发布

        tv_jianbao_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intiData();
                if ("".equals(str_jianbao_biaoti)) {
                    Toast.makeText(PublishActivity.this, "亲，怎们可以没有标题呢", Toast.LENGTH_SHORT).show();
                } else if ("".equals(str_jianbao_miaoshu)) {
                    Toast.makeText(PublishActivity.this, "亲，描述下你的宝贝", Toast.LENGTH_SHORT).show();
                } else if ("".equals(str_jianbao_price)) {
                    Toast.makeText(PublishActivity.this, "无价之宝，别闹了，留个价格呗", Toast.LENGTH_SHORT).show();
                } else if ("".equals(str_jianbao_number)) {
                    Toast.makeText(PublishActivity.this, "亲，留下电话方便联系呦", Toast.LENGTH_SHORT).show();
                } else {
                    initUploading();
                }
            }
        });
    }

    // 判断Edittext最多输入多少字符
    TextWatcher textWatcher = new TextWatcher() {

        private Toast toast;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            //TODO
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            //TODO
        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.d("TAG", "afterTextChanged    " + "str=" + s.toString());
            int len = et_jianbao_biaoti.getText().toString().length();
            int len2 = et_jianbao_miaoshu.getText().toString().length();
            if (len >= 9) {
                Toast.makeText(PublishActivity.this, "亲,最多输入10个字呦", Toast.LENGTH_SHORT).show();
                et_jianbao_biaoti.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)}); //最多输入10的字符
            }
            if (len >= 29) {
                Toast.makeText(PublishActivity.this, "亲,最多输入30个字呦", Toast.LENGTH_SHORT).show();
                et_jianbao_miaoshu.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)}); //最多输入30的字符
            }
        }
    };


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String et_jianbao_number = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(et_jianbao_number);
        }
    }


}


