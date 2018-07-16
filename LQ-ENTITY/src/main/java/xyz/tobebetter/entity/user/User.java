package xyz.tobebetter.entity.user;

import xyz.tobebetter.entity.Entity;


/**
 * Created by zhuqing on 2017/7/21.
 */
public class User extends Entity {

    private String name;
    private String password;
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;


    /**
     * 其他系统的Id

     */
    private String othersysId;
    
    /**
     * 最后一次登录时间
     */
    private Long lastLogin;


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

    public String getOthersysId() {
        return othersysId;
    }

    public void setOthersysId(String othersysId) {
        this.othersysId = othersysId;
    }

    /**
     * 手机号
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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
}
