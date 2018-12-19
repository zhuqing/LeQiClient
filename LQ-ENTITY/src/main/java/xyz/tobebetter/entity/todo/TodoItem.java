package xyz.tobebetter.entity.todo;


import xyz.tobebetter.entity.Entity;

/**
 * Created by zhuleqi on 2018/12/14.
 */
public class TodoItem extends Entity {
    private String title;
    private String detail;
    private String excuteDay;
    private String userId;


    /**
     * 名称
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 备注
     */
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * 执行时间
     */
    public String getExcuteDay() {
        return excuteDay;
    }

    public void setExcuteDay(String excuteDay) {
        this.excuteDay = excuteDay;
    }

    /**
     * 创建者的用户Id
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
