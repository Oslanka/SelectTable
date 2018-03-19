package runvr.oslanka.cnn.selecttable.bean;

import java.util.List;

/**
 * Created by cnn on 18-3-15.
 */

public class InsertContentBean {


    /**
     * code : 0
     * data : [{"code":"27","sctime":"2018-03-15 14:15:26.223","ydh":"776"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 27
         * sctime : 2018-03-15 14:15:26.223
         * ydh : 776
         */

        private String code;
        private String sctime;
        private String ydh;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSctime() {
            return sctime;
        }

        public void setSctime(String sctime) {
            this.sctime = sctime;
        }

        public String getYdh() {
            return ydh;
        }

        public void setYdh(String ydh) {
            this.ydh = ydh;
        }
    }
}
