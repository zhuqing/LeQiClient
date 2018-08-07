/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.play;

import java.util.ArrayList;
import java.util.List;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuleqi
 */
public class AudioPlayPoint extends Entity {
    
    /**
     * 第几句
     */
    private int index;

    /**
     * 开始时间
     */
    private long startTime;
    /**
     * 结束时间
     */
    private long endTime;
    /**
     * 播放的英文文本
     */
    private String enText;
    /**
     * 播放的中文翻译
     */
    private String chText;
    
    private List<short[]> recordList;

    public static List<AudioPlayPoint> toAudioPlays(String text) throws Exception {

        List<AudioPlayPoint> audioPlays = new ArrayList<>();
        String[] scentences = text.split(Consistent.SLIP_SENTENCE);
        int index = 0;
        for (String scentence : scentences) {
            AudioPlayPoint play = new AudioPlayPoint();
            String[] timeAndS = scentence.split(Consistent.SLIP_TIME_AND_TEXT);
            if (timeAndS.length < 2) {
                throw new Exception("格式不对：" + scentence);
            }
            String timeStr = timeAndS[0];
            String scenStr = timeAndS[1];

            if (!timeStr.contains(Consistent.SLIP_START_AND_END)) {
                throw new Exception("时间格式不对：" + scentence);
            }
            String[] timeArr = timeStr.split(Consistent.SLIP_START_AND_END);
            play.setStartTime(Long.valueOf(timeArr[0].trim()));
            play.setEndTime(Long.valueOf(timeArr[1].trim()));

            if (scenStr.contains(Consistent.SLIP_EN_AND_CH)) {
                String[] scenArr = scenStr.split(Consistent.SLIP_EN_AND_CH);
                play.setEnText(scenArr[0]);
                play.setChText(scenArr[1]);
            } else {
                play.setEnText(scenStr);
            }

            play.setIndex(index++);
            audioPlays.add(play);
        }
        return audioPlays;
    }

    /**
     * @return the enText
     */
    public String getEnText() {
        return enText;
    }

    /**
     * @param enText the enText to set
     */
    public void setEnText(String enText) {
        this.enText = enText;
    }

    /**
     * @return the chText
     */
    public String getChText() {
        return chText;
    }

    /**
     * @param chText the chText to set
     */
    public void setChText(String chText) {
        this.chText = chText;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the recordList
     */
    public List<short[]> getRecordList() {
        return recordList;
    }

    /**
     * @param recordList the recordList to set
     */
    public void setRecordList(List<short[]> recordList) {
        this.recordList = recordList;
    }

}
