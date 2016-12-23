package com.wxb.jianbao11.bean;

/**
 * Created by Administrator on 2016/12/22.
 */

public class Version {

    /**
     * status : 200
     * info : 成功
     * data : {"ver":"1.1.0","date":"2016-12-22 05:59:15","url":"cg_1.1.0.apk","notes":"公测版"}
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
         * ver : 1.1.0
         * date : 2016-12-22 05:59:15
         * url : cg_1.1.0.apk
         * notes : 公测版
         */

        private String ver;
        private String date;
        private String url;
        private String notes;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
