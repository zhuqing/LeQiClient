package xyz.tobebetter.entity;




/**
 * Created by zhuleqi on 2018/2/23.
 */
public class Propose extends Entity {
    private String connect;
    private String message;

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
