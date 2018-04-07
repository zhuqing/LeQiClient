package xyz.tobebetter.entity;

/**
 * Created by zhuqing on 2017/8/19.
 */

public class Greeting {
    private Long id;



    private String content;

    public Long getId() {
        if(id == null){
            return -1L;
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        if(content==null){
            return "null";
        }

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return this.getId()+":::"+this.getContent();
    }
}
