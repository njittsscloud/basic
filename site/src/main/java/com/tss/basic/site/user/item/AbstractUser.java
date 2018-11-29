package com.tss.basic.site.user.item;

import java.util.List;

/**
 * @author MQG
 * @date 2018/11/29
 */
public abstract class AbstractUser {
    protected CookieItem cookieItem;

    protected List<CookieItem> coolies;

    protected boolean god;

    protected long userId;

    protected String userName;

    protected String name;

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public abstract Integer getUserType();

    public CookieItem getCookieItem() {
        return cookieItem;
    }

    public void setCookieItem(CookieItem cookieItem) {
        this.cookieItem = cookieItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CookieItem> getCoolies() {
        return coolies;
    }

    public void setCoolies(List<CookieItem> coolies) {
        this.coolies = coolies;
    }
}
