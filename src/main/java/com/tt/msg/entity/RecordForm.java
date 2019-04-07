package com.tt.msg.entity;

import java.sql.Timestamp;

/**
 * @ClassName RecordForm
 * @Description 用于record条件查询的form实体类
 * @Author tanjiang
 * @CreateTime 2019/4/5 15:59
 * @Version 1.0
 **/

public class RecordForm {
    /**
     * 处理的文件名
     */
    private String fileName;
    /**
     * 报文类型
     */
    private String type;
    /**
     * 开始时间
     */
    private Timestamp startDate;
    /**
     * 结束时间
     */
    private Timestamp endDate;
    /**
     * 处理结果
     */
    private String result;
    /**
     * 页码
     */
    private Integer page;

    public RecordForm() {
    }

    public RecordForm(String fileName, String type, Timestamp startDate, Timestamp endDate, String result, Integer page) {
        this.fileName = fileName;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.result = result;
        this.page = page;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
