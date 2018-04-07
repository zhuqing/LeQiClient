package xyz.tobebetter.entity;


/**
 * 用户任务的执行记录
 * Created by zhuleqi on 2018/2/11.
 */
public class UserTaskRecord  extends Entity {


    private String userTaskId;
    private String userId;
    private String title;
    private String content;


    /**
     * 用户任务的id
     */
    public String getUserTaskId() {
        return userTaskId;
    }

    public void setUserTaskId(String userTaskId) {
        this.userTaskId = userTaskId;
    }

    /**
     * 用户Id
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 记录的标题
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 记录的内容
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
