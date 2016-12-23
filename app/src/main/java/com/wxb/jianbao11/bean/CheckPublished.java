package com.wxb.jianbao11.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class CheckPublished {

    /**
     * status : 200
     * info : 成功
     * data : {"size":4,"list":[{"id":112,"title":"1","image":"112_0.jpg","price":"333","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-22 10:54:41","follow":0,"state":0,"followed":false},{"id":80,"title":"123456","image":"80_0.jpg","price":"1111","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-14 06:02:39","follow":1,"state":1,"followed":false},{"id":77,"title":"1111","image":"77_0.jpg","price":"11111","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-14 05:47:54","follow":0,"state":1,"followed":false},{"id":66,"title":"789","image":"66_0.png","price":"999","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-14 03:14:04","follow":2,"state":1,"followed":false}]}
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
         * size : 4
         * list : [{"id":112,"title":"1","image":"112_0.jpg","price":"333","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-22 10:54:41","follow":0,"state":0,"followed":false},{"id":80,"title":"123456","image":"80_0.jpg","price":"1111","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-14 06:02:39","follow":1,"state":1,"followed":false},{"id":77,"title":"1111","image":"77_0.jpg","price":"11111","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-14 05:47:54","follow":0,"state":1,"followed":false},{"id":66,"title":"789","image":"66_0.png","price":"999","contact":"9女士","head":"99999999999_HEAD.jpg","issue_time":"2016-12-14 03:14:04","follow":2,"state":1,"followed":false}]
         */

        private int size;
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
            /**
             * id : 112
             * title : 1
             * image : 112_0.jpg
             * price : 333
             * contact : 9女士
             * head : 99999999999_HEAD.jpg
             * issue_time : 2016-12-22 10:54:41
             * follow : 0
             * state : 0
             * followed : false
             */

            private int id;
            private String title;
            private String image;
            private String price;
            private String contact;
            private String head;
            private String issue_time;
            private int follow;
            private int state;
            private boolean followed;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getIssue_time() {
                return issue_time;
            }

            public void setIssue_time(String issue_time) {
                this.issue_time = issue_time;
            }

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
            }
        }
    }
}
