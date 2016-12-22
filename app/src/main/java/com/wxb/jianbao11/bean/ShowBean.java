package com.wxb.jianbao11.bean;

/**
 * Created by Administrator on 2016/12/19.
 */

public class ShowBean {

    /**
     * status : 200
     * info : 成功
     * data : {"size":1,"list":[{"id":111,"title":"1","image":"111_0.jpg","price":"1","issue_time":"2016-12-06 11:23:12","state":0}]}
     */

    private String status;
    private String info;
    private Goods data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Goods getData() {
        return data;
    }

    public void setData(Goods data) {
        this.data = data;
    }

    
}
