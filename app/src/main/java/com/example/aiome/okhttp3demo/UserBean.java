package com.example.aiome.okhttp3demo;

/**
 * Created by Aiome on 2017/2/14.
 */

public class UserBean {

    /**
     * constant : {"resourceServer":"http://aiome.top/upload"}
     * code : 1
     * message : login success
     * info : {"birthday":"1996-02-28","majorId":"10006018","flag":1,"sex":0,"mobile":"15230106379","travelIdUser":"09aa38db349e4f9289d2bd4fc7d9f18b","profileImage":"upload/images/2016/12/26/1.jpg","stars":2.1,"userName":"马红岩","creatDate":1479532424056,"userId":"dcc2d7bf7f2a4c089f142a35af2f1318","point":9100,"enrollment":"2014","travelIdGuider":"a99f650668cc4ed8a81de6f18f60ecdb","constellation":"天蝎座","schoolId":"11006","motto":"这个家伙很懒,什么都没有留下!"}
     * token : d7ecbb5a1a4f4e21918a648c4ae19235g7Q3s6
     */

    private ConstantBean constant;
    private int code;
    private String message;
    private InfoBean info;
    private String token;

    public ConstantBean getConstant() {
        return constant;
    }

    public void setConstant(ConstantBean constant) {
        this.constant = constant;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class ConstantBean {
        /**
         * resourceServer : http://aiome.top/upload
         */

        private String resourceServer;

        public String getResourceServer() {
            return resourceServer;
        }

        public void setResourceServer(String resourceServer) {
            this.resourceServer = resourceServer;
        }
    }

    public static class InfoBean {
        /**
         * birthday : 1996-02-28
         * majorId : 10006018
         * flag : 1
         * sex : 0
         * mobile : 15230106379
         * travelIdUser : 09aa38db349e4f9289d2bd4fc7d9f18b
         * profileImage : upload/images/2016/12/26/1.jpg
         * stars : 2.1
         * userName : 马红岩
         * creatDate : 1479532424056
         * userId : dcc2d7bf7f2a4c089f142a35af2f1318
         * point : 9100
         * enrollment : 2014
         * travelIdGuider : a99f650668cc4ed8a81de6f18f60ecdb
         * constellation : 天蝎座
         * schoolId : 11006
         * motto : 这个家伙很懒,什么都没有留下!
         */

        private String birthday;
        private String majorId;
        private int flag;
        private int sex;
        private String mobile;
        private String travelIdUser;
        private String profileImage;
        private double stars;
        private String userName;
        private long creatDate;
        private String userId;
        private int point;
        private String enrollment;
        private String travelIdGuider;
        private String constellation;
        private String schoolId;
        private String motto;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMajorId() {
            return majorId;
        }

        public void setMajorId(String majorId) {
            this.majorId = majorId;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTravelIdUser() {
            return travelIdUser;
        }

        public void setTravelIdUser(String travelIdUser) {
            this.travelIdUser = travelIdUser;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public double getStars() {
            return stars;
        }

        public void setStars(double stars) {
            this.stars = stars;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getCreatDate() {
            return creatDate;
        }

        public void setCreatDate(long creatDate) {
            this.creatDate = creatDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getEnrollment() {
            return enrollment;
        }

        public void setEnrollment(String enrollment) {
            this.enrollment = enrollment;
        }

        public String getTravelIdGuider() {
            return travelIdGuider;
        }

        public void setTravelIdGuider(String travelIdGuider) {
            this.travelIdGuider = travelIdGuider;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }
    }
}
