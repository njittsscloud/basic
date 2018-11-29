package com.tss.basic.site.user.item;

/**
 * @author MQG
 * @date 2018/11/29
 */
public class CookieItem {
    
    /**
     * cookie名称
     */
    private String name;

    /**
     * cookie值
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CookieItem{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
