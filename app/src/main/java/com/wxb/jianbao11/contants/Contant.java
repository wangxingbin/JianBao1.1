package com.wxb.jianbao11.contants;

/**
 * Created by Administrator on 2016/12/19.
 */

public class Contant {
    public static final String  MAIN = "http://192.168.4.188/Goods";
    // 已发布商品查询
    public static final String CHECKPUBLISHED = MAIN + "/app/user/issue_list.json";
    // 邀请码
    public static final String  InvitationCode = MAIN + "/app/user/invite.json";
    // 个人信息
    public static final String GeRenXinXi = MAIN + "/app/user/info.json";
    // 已关注商品
    public static final String GuanZhuList = MAIN + "/app/user/follow_list.json";
    // 上传头像
    public static final String TouXiang = MAIN + "/app/user/upload.json";
    // 图片前缀
    public static final String IMGQZ = "http://192.168.4.188/Goods/uploads/";

    public static  final String CHAXUN=MAIN+"/app/item/list.json";

    public static  final String XIANGQING=MAIN+"/app/item/detail.json";

    public static  final String GUANZHU=MAIN+"/app/item/follow.json";

    //商品变更
    public static  final String BIANGENG=MAIN+"/app/item/modify.json";

    public static  final  String LAND=MAIN+"/app/common/login.json";
    public  static  final String RESGISTER=MAIN+"/app/common/register.json";
}
