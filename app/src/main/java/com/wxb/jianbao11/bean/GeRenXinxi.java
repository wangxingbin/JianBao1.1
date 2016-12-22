package com.wxb.jianbao11.bean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class GeRenXinxi {

    /**
     * status : 200
     * info : 成功
     * data : {"id":100093,"mobile":"99999999999","name":"99","gender":"女","idcard":"99999999999_CARD.jpeg","photo":"99999999999_HEAD.jpg","qq":"","wechat":"","email":"","last_time":"2016-12-21 02:37:38","state":0,"normal":true}
     */

    private String status;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 100093
         * mobile : 99999999999
         * name : 99
         * gender : 女
         * idcard : 99999999999_CARD.jpeg
         * photo : 99999999999_HEAD.jpg
         * qq :
         * wechat :
         * email :
         * last_time : 2016-12-21 02:37:38
         * state : 0
         * normal : true
         */

        private int id;
        private String mobile;
        private String name;
        private String gender;
        private String idcard;
        private String photo;
        private String qq;
        private String wechat;
        private String email;
        private String last_time;
        private int state;
        private boolean normal;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public boolean isNormal() {
            return normal;
        }

        public void setNormal(boolean normal) {
            this.normal = normal;
        }
    }
}
