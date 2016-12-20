package com.wxb.jianbao11.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SinglePage;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.fragment.EntryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 诺古 on 2016/12/20.
 */

public class GuidePageActivity  extends AbsGuideActivity{

    //构建引导页的各个页面，每个页面用SinglePage来描述
    public List<SinglePage> buildGuideContent() {
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mBackground = getResources().getDrawable(R.drawable.bg_page_01,null);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = getResources().getDrawable(R.drawable.bg_page_02,null);
        guideContent.add(page02);

        SinglePage page03 = new SinglePage();
        page03.mBackground = getResources().getDrawable(R.drawable.bg_page_03,null);
        guideContent.add(page03);

        SinglePage page04 = new SinglePage();
        page04.mBackground = getResources().getDrawable(R.drawable.bg_page_04,null);
        guideContent.add(page04);

        SinglePage page05 = new SinglePage();
        page05.mCustomFragment = new EntryFragment();
        guideContent.add(page05);

        return guideContent;
    }

    //是否显示dot
    public boolean drawDot() {
        return true;
    }

    //设置dot未选中时的图标
    public Bitmap dotDefault() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default);
    }

    //设置dot选中时的图标
    public Bitmap dotSelected() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected);
    }

    //你需要在资源描述中添加<item name="guide_container" type="id"/>
    public int getPagerId() {
        return R.id.guide_container;
    }
    public void entryApp() {
        finish();
    }

}
