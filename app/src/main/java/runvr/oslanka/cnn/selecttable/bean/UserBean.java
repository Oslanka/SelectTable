package runvr.oslanka.cnn.selecttable.bean;

/**
 * Created by cnn on 18-3-17.
 */

public class UserBean {


    /**
     * isLogin : true
     * code : 0
     * data : {"password":"1234","nickName":"sdfsdf","username":"a"}
     * update : true
     */

    private boolean isLogin;
    private int code;
    private DataBean data;
    private boolean update;

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public static class DataBean {
        /**
         * password : 1234
         * nickName : sdfsdf
         * username : a
         */

        private String password;
        private String nickName;
        private String username;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
