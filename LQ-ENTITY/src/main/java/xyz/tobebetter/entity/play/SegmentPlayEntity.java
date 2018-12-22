package xyz.tobebetter.entity.play;

import com.sun.org.apache.xpath.internal.operations.String;
import xyz.tobebetter.entity.Entity;

/**
 * Created by zhuleqi on 2018/12/21.
 */
public class SegmentPlayEntity extends Entity {
    private Integer startTime;
    private Integer endTime;
    private String filePaht;
    private String segmentId;


    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getFilePaht() {
        return filePaht;
    }

    public void setFilePaht(String filePaht) {
        this.filePaht = filePaht;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }
}
