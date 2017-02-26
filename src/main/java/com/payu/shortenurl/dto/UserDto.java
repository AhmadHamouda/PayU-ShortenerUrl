package com.payu.shortenurl.dto;

import java.io.Serializable;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String userName;

    private String password;

    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
