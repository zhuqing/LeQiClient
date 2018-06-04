package xyz.tobebetter.entity;



/**
 * Created by zhuleqi on 2018/2/23.
 */
public class Message {
    public final static int ERROR = -1;
    public final static int SUCCESS = 0;
    private String message;
    private int status;
    /**
     * 当前页码
     */
    private int page;
    /**
     * 总页数
     */
    private int totalPage;
    private String data;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 状态　
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 当前页码
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * 当前页码
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 总页数
     * @return the totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 总页数
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
