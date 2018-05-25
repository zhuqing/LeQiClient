/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.sourceitem;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 选择组件的数据源
 *
 * @author zhuqing
 */
public class SourceItem<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(SourceItem.class.getName());
    /**
     * 唯一值
     */
    private String id;
    /**
     * 显示的值
     */
    private String display;
    /**
     * 真实表示的值
     */
    private T value;
    /**
     * 短拼
     */
    private String shortPY;
    /**
     * 助记码
     */
    private String mnemonicCode;
    /**
     * 子数据源
     */
    private List<SourceItem> children;

    public SourceItem() {
        this.id = UUID.randomUUID().toString();
    }

    public SourceItem(String id, String display, T value) {
        this.id = id;
        this.display = display;
        this.value = value;
    }

    public SourceItem(String display, T value, String shortPY, String mnemonicCode) {
        this();
        this.display = display;
        this.value = value;
        this.shortPY = shortPY;
        this.mnemonicCode = mnemonicCode;
    }

    public SourceItem(String id, String display, T value, String shortPY, String mnemonicCode) {
        this.id = id;
        this.display = display;
        this.value = value;
        this.shortPY = shortPY;
        this.mnemonicCode = mnemonicCode;
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
     * @return the display
     */
    public String getDisplay() {
        if (display == null) {
            LOGGER.error(this.toString());
            return "null";
        }
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return the shortPY
     */
    public String getShortPY() {
        if (shortPY == null) {
            return "";
        }
        return shortPY;
    }

    /**
     * @param shortPY the shortPY to set
     */
    public void setShortPY(String shortPY) {
        this.shortPY = shortPY;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SourceItem<?> other = (SourceItem<?>) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * @return the mnemonicCode
     */
    public String getMnemonicCode() {
        if (mnemonicCode == null) {
            return "";
        }
        return mnemonicCode;
    }

    /**
     * @param mnemonicCode the mnemonicCode to set
     */
    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    @Override
    public String toString() {
        return "SourceItem{" + "id=" + id + ", display=" + display + ", value=" + value + ", shortPY=" + shortPY + ", mnemonicCode=" + mnemonicCode + '}';
    }

    /**
     * 子数据源
     * @return the children
     */
    public List<SourceItem> getChildren() {
        return children;
    }

    /**
     * 子数据源
     * @param children the children to set
     */
    public void setChildren(List<SourceItem> children) {
        this.children = children;
    }

}
