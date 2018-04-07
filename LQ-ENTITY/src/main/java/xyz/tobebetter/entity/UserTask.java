package xyz.tobebetter.entity;


/**
 * Created by zhuleqi on 2018/2/10.
 */

public class UserTask   extends Entity {

    public final static  Integer STATUS_SYS = 0;
    public final static  Integer STATUS_CUSTOM_TIME = 1;
    public final static  Integer STATUS_CUSTOM_TIMEING = 2;

    private String title;
    /**
     *任务时长的秒数 
     */
    private Integer seconds;
    /**
     * 任务开始时间
     */
    private Long startDate;
    private String userId;


    /**
     * 任务名称
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



  

    @Override
    public UserTask clone(){
        UserTask userTask = new UserTask();
        userTask.setId(this.getId());
        userTask.setTitle(this.getTitle());
        userTask.setSeconds(seconds);
        userTask.setStartDate(startDate);
        userTask.setUserId(userId);
      
        userTask.setStatus(this.getStatus());
        userTask.setCreateDate(this.getCreateDate());
        userTask.setUpdateDate(this.getUpdateDate());
        return userTask;
    }

    /**
     * 任务时长的秒数
     * @return the seconds
     */
    public Integer getSeconds() {
        return seconds;
    }

    /**
     * 任务时长的秒数
     * @param seconds the seconds to set
     */
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    /**
     * 任务开始时间
     * @return the startDate
     */
    public Long getStartDate() {
        return startDate;
    }

    /**
     * 任务开始时间
     * @param startDate the startDate to set
     */
    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


}
