package runvr.oslanka.cnn.selecttable.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cnn on 18-3-16.
 */

public class SelectPicBean {

    /**
     * code : 0
     * size : 2
     * data : [{"id":"2283","sctime":"2018-03-15 14:32:18.993","ydh":"1397"},{"id":"2284","sctime":"2018-03-15 14:33:52.223","ydh":"1397"}]
     */

    private int code;
    private int size;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2283
         * sctime : 2018-03-15 14:32:18.993
         * ydh : 1397
         */

        private String id;
        private String sctime;
        private long ydh;
        private String who;
        private String ydxh;

        public String getYdxh() {
            return ydxh;
        }

        public void setYdxh(String ydxh) {
            this.ydxh = ydxh;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSctime() {
            return sctime;
        }

        public void setSctime(String sctime) {
            this.sctime = sctime;
        }

        public String getYdh() {
            return  new BigDecimal(ydh).toPlainString();
        }

    }
}
