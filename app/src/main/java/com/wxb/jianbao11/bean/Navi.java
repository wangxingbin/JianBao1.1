package com.wxb.jianbao11.bean;

import java.util.List;

/**
 * Created by admin on 2016/12/21.
 */

public class Navi {

    /**
     * status : 200
     * info : 成功
     * data : {"size":9,"list":[{"idx":0,"url":"NAVI_1.png","notes":null},{"idx":1,"url":"NAVI_2.png","notes":null},{"idx":2,"url":"NAVI_3.png","notes":null},{"idx":3,"url":"NAVI_4.png","notes":null},{"idx":4,"url":"NAVI_5.png","notes":null},{"idx":5,"url":"NAVI_6.png","notes":null},{"idx":6,"url":"NAVI_7.png","notes":null},{"idx":7,"url":"NAVI_8.png","notes":null},{"idx":8,"url":"NAVI_9.png","notes":null}]}
     */

    private String status;
    private String info;
    /**
     * size : 9
     * list : [{"idx":0,"url":"NAVI_1.png","notes":null},{"idx":1,"url":"NAVI_2.png","notes":null},{"idx":2,"url":"NAVI_3.png","notes":null},{"idx":3,"url":"NAVI_4.png","notes":null},{"idx":4,"url":"NAVI_5.png","notes":null},{"idx":5,"url":"NAVI_6.png","notes":null},{"idx":6,"url":"NAVI_7.png","notes":null},{"idx":7,"url":"NAVI_8.png","notes":null},{"idx":8,"url":"NAVI_9.png","notes":null}]
     */

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
        private int size;
        /**
         * idx : 0
         * url : NAVI_1.png
         * notes : null
         */

        private List<ListBean> list;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int idx;
            private String url;
            private Object notes;

            public int getIdx() {
                return idx;
            }

            public void setIdx(int idx) {
                this.idx = idx;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getNotes() {
                return notes;
            }

            public void setNotes(Object notes) {
                this.notes = notes;
            }
        }
    }
}
