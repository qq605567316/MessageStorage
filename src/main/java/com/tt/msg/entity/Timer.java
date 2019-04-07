package com.tt.msg.entity;

/**
 * @ClassName Timer
 * @Description 定时器配置
 * @Author tanjiang
 * @CreateTime 2019/1/15 11:07
 * @Version 1.0
 **/

public class Timer {
    /**
     * 序列号
     */
    private Long seq;
    /**
     * 名称
     */
    private String name;
    /**
     *解析报文类型
     */
    private String type;
    /**
     * 定时器状态 0:正在执行  1:停止
     */
    private String status;
    /**
     * 解析文件路径
     */
    private String filePath;
    /**
     * 解析频率,cron表达式
     */
    private String cronExpression;


    public Timer() {
    }

    public Timer(Long seq, String status) {
        this.seq = seq;
        this.status = status;
    }

    public Timer(String name, String type, String status, String filePath, String cronExpression) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.filePath = filePath;
        this.cronExpression = cronExpression;
    }

    public Timer(Long seq, String name, String type, String status, String filePath,
            String cronExpression) {
        this.seq = seq;
        this.name = name;
        this.type = type;
        this.status = status;
        this.filePath = filePath;
        this.cronExpression = cronExpression;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
