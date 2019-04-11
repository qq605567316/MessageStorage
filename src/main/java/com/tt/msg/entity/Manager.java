package com.tt.msg.entity;

/**
 * @ClassName Manager
 * @Description 用户实体类
 * @Author tanjiang
 * @CreateTime 2019/1/12 10:06
 * @Version 1.0
 **/

public class Manager {
    private int userLevel;
    private String username;
    private String password;
    /**
     * level为0，levelName为超级管理员；level为1，levelName为普通管理员
     */
    private String levelName;

    public Manager() {
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
        this.setLevelName(userLevel);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(int userLevel) {
        if (userLevel == 0) {
            this.levelName = "超级管理员";
        }
        if (userLevel == 1) {
            this.levelName = "普通管理员";
        }
    }
}
