package com.wxb.jianbao11.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.wxb.jianbao11.MainActivity;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.activity.AttentionActivity;
import com.wxb.jianbao11.activity.GuideActivity;
import com.wxb.jianbao11.activity.MessageActivity;
import com.wxb.jianbao11.activity.PublishedActivity;
import com.wxb.jianbao11.activity.SettingsActivity;
import com.wxb.jianbao11.bean.CodeBeen;
import com.wxb.jianbao11.bean.GeRenXinxi;
import com.wxb.jianbao11.bean.Uphoto;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.MyCallBack;
import com.wxb.jianbao11.utils.MyOkhttp;
import com.wxb.jianbao11.utils.PhotoPostUtils;
import com.wxb.jianbao11.utils.TakePhotoPopWin;

import java.io.File;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 诺古 on 2016/12/19.
 */
public class MineFragment extends Fragment {
    @InjectView(R.id.mine_iv_photo)
    SimpleDraweeView mineIvPhoto;
    @InjectView(R.id.mine_tv_name)
    TextView mineTvName;
    @InjectView(R.id.mine_tv_phoneNum)
    TextView mineTvPhoneNum;
    @InjectView(R.id.mine_ll_message)
    LinearLayout mineLlMessage;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.mine_ll_publish)
    LinearLayout mineLlPublish;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.mine_ll_attend)
    LinearLayout mineLlAttend;
    @InjectView(R.id.mine_ll_settings)
    LinearLayout mineLlSettings;
    @InjectView(R.id.mine_ll_yindao)
    LinearLayout mineLlYindao;
    @InjectView(R.id.mine_tv_invitationCode)
    TextView mineTvInvitationCode;
    @InjectView(R.id.mine_denglu)
    LinearLayout mineDenglu;
    @InjectView(R.id.mine_denglued)
    LinearLayout mineDenglued;
    @InjectView(R.id.mine_btn)
    Button mineBtn;
    private String token;
    private TakePhotoPopWin photoPopWin;
    private String facePath;
    private String TouXPath = Contant.TouXiang;

    private HashMap<String, String> map;
    private Handler mHandler = new Handler();

    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private Uri uri;
    private File imageFile;
    private String selecImaPath;
    private Uri originalUri;
    private MainActivity activity;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.inject(this, view);
        activity = (MainActivity) getActivity();
        fm = activity.getSupportFragmentManager();
        ft = fm.beginTransaction();
        facePath = Environment.getExternalStorageDirectory() + "/face.jpg";
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        token = sp.getString("token", "");
        map = new HashMap<>();
        map.put("token", token);
        initData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 拍照后获取返回值，这里获取到的是原始图片。
        if (requestCode == PHOTO_REQUEST_CAREMA && resultCode == Activity.RESULT_OK) {
            File file = new File(facePath);
            Uri uri = Uri.fromFile(file);
            mineIvPhoto.setImageURI(uri);
            upLoadFaceIcon(file);
        } else if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (photoPopWin.isShowing()) {
                photoPopWin.dismiss();
            }
            if (data != null) {
                originalUri = data.getData();
                selecImaPath = originalUri.toString();
                if (selecImaPath.startsWith("file:///s")) {

                } else {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().managedQuery(originalUri, proj, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        String string = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        selecImaPath = cursor.getString(column_index);
                    }
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearCache(Uri.parse(selecImaPath));
                        requestImage(selecImaPath);
                        String replace = selecImaPath.replace("file://", "");
                        upLoadFaceIcon(new File(replace));
                    }
                }, 500);
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (photoPopWin.isShowing()) {
                photoPopWin.dismiss();
            }

            mineIvPhoto.setImageURI(imageFile.getAbsolutePath());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.mine_btn, R.id.mine_iv_photo, R.id.mine_ll_message, R.id.mine_ll_publish, R.id.mine_ll_attend, R.id.mine_ll_settings, R.id.mine_ll_yindao, R.id.mine_tv_invitationCode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_btn:
                SharedPreferences share= getActivity().getSharedPreferences("TOKEN",MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit();
                edit.putString("token", "");
                edit.putBoolean("isLogin",false);
                edit.commit();
                ft=fm.beginTransaction();
                ft.hide(activity.mineFragment);
                ft.show(activity.goodsFragment);
                ft.commit();
               // startActivity(new Intent(getActivity(), Login.class));
               // getActivity().finish();

                break;
            case R.id.mine_iv_photo:
                showPop(view);
                break;
            case R.id.mine_ll_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.mine_ll_publish:
                startActivity(new Intent(getActivity(), PublishedActivity.class));
                break;
            case R.id.mine_ll_attend:
                startActivity(new Intent(getActivity(), AttentionActivity.class));
                break;
            case R.id.mine_ll_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.mine_ll_yindao:
                startActivity(new Intent(getActivity(), GuideActivity.class));
                break;
            case R.id.mine_tv_invitationCode:
                initIn();
                break;
        }
    }           

    // 个人信息
    private void initData() {
        final String path = Contant.GeRenXinXi;
        MyOkhttp.getInstance().doRequest(MineFragment.this.getActivity(),path, MyOkhttp.RequestType.POST, map, new MyCallBack() {

            private String mobile;
            private String name;
            private String photo;

            @Override
            public void loading() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(Object o) {
                if (o == null || !(o instanceof GeRenXinxi)) {
                    return;
                }

                GeRenXinxi geRenXinxi = (GeRenXinxi) o;
                if (geRenXinxi.getData()==null){
                    return;
                }
                mobile = geRenXinxi.getData().getMobile();
                name = geRenXinxi.getData().getName();
                photo = geRenXinxi.getData().getPhoto();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (photo == null || photo.isEmpty()) {
                            mineIvPhoto.setImageResource(R.mipmap.morentx);
                        } else if (!photo.isEmpty() || photo != null) {
                            String path = Contant.IMGQZ + photo;
                            Uri imgurl = Uri.parse(path);
                            clearCache(imgurl);
                            mineIvPhoto.setImageURI(imgurl);
                        }
                        mineTvName.setText(name);
                        mineTvPhoneNum.setText(mobile);
                    }
                });
            }

            @Override
            public void onError() {

            }

        }, GeRenXinxi.class);

    }

    private void clearCache(Uri imgurl) {
        // 清除Fresco对这条验证码的缓存
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(imgurl);
        imagePipeline.evictFromDiskCache(imgurl);
        // combines above two lines
        imagePipeline.evictFromCache(imgurl);

        imagePipeline.clearCaches();
    }

    // 相册
    private void pictureschose() {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);

        /* 取得相片后返回本画面 */
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    // 相机
    public void photo() {
        imageFile = new File(facePath);
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置拍照后保存的图片存储在文件中
        uri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 启动activity并获取返回数据
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    //上传图片
    private void upLoadFaceIcon(File file) {
        PhotoPostUtils.getData(new PhotoPostUtils.GetRegisterData() {
            @Override
            public void setRegisterData(Object o) {
                if (o != null && o instanceof Uphoto) {
                    Uphoto o1 = (Uphoto) o;
                    String status = o1.getStatus();
                    if ("200".equals(status)) {
                    }
                }
            }
        });
        PhotoPostUtils.upLoad(file, getActivity(), TouXPath, "photo", map, Uphoto.class);
    }

    private void requestImage(String url) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setAutoRotateEnabled(true)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();

        mineIvPhoto.setController(controller);
    }

    private void showPop(View v) {
        photoPopWin = new TakePhotoPopWin(getActivity(), onClickListener);
        photoPopWin.showAtLocation(mineIvPhoto, Gravity.CENTER, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_pick_photo:
                    pictureschose();
                    break;
                case R.id.btn_take_photo:
                    photo();
                    photoPopWin.dismiss();
                    break;
            }
        }
    };

    // 邀请码
    public void initIn() {
        String path = Contant.InvitationCode;
        MyOkhttp.getInstance().doRequest(MineFragment.this.getActivity(),path, MyOkhttp.RequestType.POST, map, new MyCallBack() {

            private int state;
            private String code;

            @Override
            public void loading() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(Object o) {
                if (o == null || !(o instanceof CodeBeen)) {
                    return;
                }
                CodeBeen codeBeen = (CodeBeen) o;
                String status = codeBeen.getStatus();
                if (status.equals("200")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getActivity(), "获取邀请码成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "获取邀请码失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                CodeBeen.DataBean data = codeBeen.getData();
                if (data == null) {
                    return;
                }

                code = data.getCode();

                state = data.getState();

                if (state == 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mineTvInvitationCode.setText(code);
                            mineTvInvitationCode.setTextColor(getResources().getColor(R.color.black));
                            mineTvInvitationCode.setTextIsSelectable(true);
                        }
                    });
                } else if (state == 1) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mineTvInvitationCode.setText(code + " 已失效");
                            mineTvInvitationCode.setTextColor(getResources().getColor(R.color.red));
                            mineTvInvitationCode.setTextIsSelectable(false);
                        }
                    });
                }

            }

            @Override
            public void onError() {

            }
        }, CodeBeen.class);
    }

}
