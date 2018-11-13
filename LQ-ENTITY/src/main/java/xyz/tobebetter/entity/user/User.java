package xyz.tobebetter.entity.user;

import xyz.tobebetter.entity.Entity;


/**
 * Created by zhuqing on 2017/7/21.
 */
public class User extends Entity {

    /**
     * 匿名
     */
    private String name;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * VIP截止日期
     */
    private String vipLastData;
    /**
     * 手机号码
     */
    private String phoneNumber;


    /**
     * 其他系统的Id

     */
    private String otherSysId;
    
    /**
     * 最后一次登录时间
     */
    private Long lastLogin;
    
    /**
     * 用户类型，0注册用户，1QQ用户，2微信用户，3微博用户
     */
    private Integer type;
    
    /**
     * 用户图像
     */
    private String imagePath;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
    /**
     * 最后一次登录时间
     * @return the lastLogin
     */
    public Long getLastLogin() {
        return lastLogin;
    }

    /**
     * 最后一次登录时间
     * @param lastLogin the lastLogin to set
     */
    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * @return the vipLastData
     */
    public String getVipLastData() {
        return vipLastData;
    }

    /**
     * @param vipLastData the vipLastData to set
     */
    public void setVipLastData(String vipLastData) {
        this.vipLastData = vipLastData;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the otherSysId
     */
    public String getOtherSysId() {
        return otherSysId;
    }

    /**
     * @param otherSysId the otherSysId to set
     */
    public void setOtherSysId(String otherSysId) {
        this.otherSysId = otherSysId;
    }

    /**
     * @return the sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }
}
