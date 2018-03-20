package runvr.oslanka.cnn.selecttable.bean;

import java.util.List;

/**
 * Created by cnn on 18-3-14.
 */

public class SelectBean {

    /**
     * code : 0
     * size : 3041
     * data : [{"tyrq":"2017-11-29 00:00:00.0","ydxh":"20171129","yhzh":"福建福州","jbren":"漳州","ydh":"1397","tyr":"联迪","shdw":"盛辉"},{"tyrq":"2017-11-29 00:00:00.0","ydxh":"23317120152","yhzh":"云南昆明","jbren":"漳州","ydh":"1399","tyr":"联迪","shdw":"魏雪芳"},{"tyrq":"2017-11-29 00:00:00.0","ydxh":"23317120159","yhzh":"天津","jbren":"漳州","ydh":"1401","tyr":"联迪","shdw":"刘庆"},{"tyrq":"2017-11-29 00:00:00.0","ydxh":"23317120162","yhzh":"湖南益阳","jbren":"漳州","ydh":"1403","tyr":"联迪","shdw":"郭澍澜"},{"tyrq":"2017-11-27 00:00:00.0","ydxh":"20171127","yhzh":"福建福州","jbren":null,"ydh":"1319","tyr":"联迪","shdw":"盛辉"},{"tyrq":"2017-11-27 00:00:00.0","ydxh":"23317111279","yhzh":"湖南益阳","jbren":"漳州","ydh":"1320","tyr":"联迪","shdw":"刘濒炜"},{"tyrq":"2017-11-27 00:00:00.0","ydxh":"23317120003","yhzh":"天津","jbren":"漳州","ydh":"1321","tyr":"联迪","shdw":"李明非"},{"tyrq":"2017-11-27 00:00:00.0","ydxh":"23317120005","yhzh":"湖南长沙","jbren":"漳州","ydh":"1322","tyr":"联迪","shdw":"蒋友鑫"},{"tyrq":"2017-11-27 00:00:00.0","ydxh":"23317120006","yhzh":"福建福州","jbren":"漳州","ydh":"1323","tyr":"联迪","shdw":"王闽军"},{"tyrq":"2017-11-27 00:00:00.0","ydxh":"23317120007","yhzh":"福建福州","jbren":"漳州","ydh":"1324","tyr":"联迪","shdw":"吴丹"}]
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
         * tyrq : 2017-11-29 00:00:00.0
         * ydxh : 20171129
         * yhzh : 福建福州
         * jbren : 漳州
         * ydh : 1397
         * tyr : 联迪
         * shdw : 盛辉
         */

        private String tyrq;
        private String ydxh;
        private String yhzh;
        private String jbren;
        private String ydh;
        private String tyr;
        private String shdw;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getTyrq() {
            return tyrq;
        }

        public void setTyrq(String tyrq) {
            this.tyrq = tyrq;
        }

        public String getYdxh() {
            return ydxh;
        }

        public void setYdxh(String ydxh) {
            this.ydxh = ydxh;
        }

        public String getYhzh() {
            return yhzh;
        }

        public void setYhzh(String yhzh) {
            this.yhzh = yhzh;
        }

        public String getJbren() {
            return jbren;
        }

        public void setJbren(String jbren) {
            this.jbren = jbren;
        }

        public String getYdh() {
            return ydh;
        }

        public void setYdh(String ydh) {
            this.ydh = ydh;
        }

        public String getTyr() {
            return tyr;
        }

        public void setTyr(String tyr) {
            this.tyr = tyr;
        }

        public String getShdw() {
            return shdw;
        }

        public void setShdw(String shdw) {
            this.shdw = shdw;
        }
    }
}
