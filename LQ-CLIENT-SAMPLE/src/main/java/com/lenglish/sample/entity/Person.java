/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lenglish.sample.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author zhuqing
 */
public class Person implements Serializable {

    private String id;
    private String name;
    private String gender;
    private Integer age;
    private Date birthday;

    private Person parent;

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

    public Person() {

    }

    public Person(String id) {
        this.id = id;
    }

    private Person(String id, String name) {
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

    /**
     * @return the parent
     */
    public Person getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Person parent) {
        this.parent = parent;
    }

    public static Person createPerson(String id, String name) {
        Person p = new Person();
        p.setId(id);
        p.setName(name);

        p.setGender("男");
        p.setAge(Double.valueOf(Math.random() * 100 + "").intValue());
        p.setLoader("1");
        p.setCodeId(100.0235);
        p.setUnitId(1l);
//        String[] hobbies = new String[]{"music", "swim"};
        Set hobbies = new HashSet();
        hobbies.add("music");

        p.setHobbies(hobbies);

        p.setPhoneticize("拼音");
        p.setPhoneticizeValue("PY");
        Date d = new Date();
        p.setStartTime(d.getTime() - 6 * 24 * 60 * 60 * 1000);

        return p;
    }

    public static List<Person> createPersons(String name) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            persons.add(createPerson(i + "", name + i));
        }
        return persons;
    }

    public static List<Person> createPersons() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            persons.add(createPerson(i + "", "robbie" + i));
        }
        return persons;
    }

}
