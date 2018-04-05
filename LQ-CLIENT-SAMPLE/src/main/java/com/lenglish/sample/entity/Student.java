/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lenglish.sample.entity;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author zhuqing
 */
public class Student {
    
    private String id;
    private String name;
    private String gender;
    private Integer age;
    private Date birthday;

    private double codeId;
    private String loader;

    private Set hobbies;

    private String phoneticize;

    private String phoneticizeValue;

    private String unit;

    private long unitId;

    private Long startTime;

    private byte[] imagebytes;

    private String filename;

   

    public Student(String id) {
        this.id = id;
    }

    private Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLoader() {
        return loader;
    }

    public void setLoader(String loader) {
        this.loader = loader;
    }

    public double getCodeId() {
        return codeId;
    }

    public void setCodeId(double codeId) {
        this.codeId = codeId;
    }

    public Set getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set hobbies) {
        this.hobbies = hobbies;
    }

    public String getPhoneticize() {
        return phoneticize;
    }

    public void setPhoneticize(String phoneticize) {
        this.phoneticize = phoneticize;
    }

    public String getPhoneticizeValue() {
        return phoneticizeValue;
    }

    public void setPhoneticizeValue(String phoneticizeValue) {
        this.phoneticizeValue = phoneticizeValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public byte[] getImagebytes() {
        return imagebytes;
    }

    public void setImagebytes(byte[] imagebytes) {
        this.imagebytes = imagebytes;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

}
