package xyz.tobebetter.entity.todo;


import xyz.tobebetter.entity.Entity;

/**
 * Created by zhuleqi on 2018/12/14.
 */
public class UserTodo extends Entity{
    private String userId;
    private String todoItemId;
    private Long excuteDate;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTodoItemId() {
        return todoItemId;
    }

    public void setTodoItemId(String todoItemId) {
        this.todoItemId = todoItemId;
    }

    /**
     * 执行日期
     * 使用年月日拼的值
     */
    public Long getExcuteDate() {
        return excuteDate;
    }

    public void setExcuteDate(Long excuteDate) {
        this.excuteDate = excuteDate;
    }
}
