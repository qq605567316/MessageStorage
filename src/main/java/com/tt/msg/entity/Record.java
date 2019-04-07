package com.tt.msg.entity;


import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName Record
 * @Description 用于记录报文处理结果
 * @Author tanjiang
 * @CreateTime 2019/3/29 9:39
 * @Version 1.0
 **/

public class Record {
    /**
     * 序列号
     */
    private Long seq;
    /**
     * 处理的文件名
     */
    private String fileName;
    /**
     * 处理日期
     */
    private Timestamp delDate;
    /**
     * 处理报文类型
     */
    private String type;
    /**
     * 处理结果 0:失败 1:成功
     */
    private String result;
    /**
     * 失败信息 若成功则为null
     */
    private String failMsg;
    /**
     * 成功时存储的的处理后放入对应表的seq 若失败，则为null
     */
    private Long sucSeq;

    public Record() {
    }

    /**
     * 处理失败时调用的构造器
     * @param type
     * @param fileName
     * @param failMsg
     */
    public Record(String fileName, String type, String failMsg) {
        this.delDate = new Timestamp(System.currentTimeMillis());
        this.fileName = fileName;
        this.type = type;
        this.result = "0";
        this.failMsg = failMsg;
    }

    /**
     * 处理成功时调用的构造器
     * @param type
     * @param fileName
     * @param sucSeq
     */
    public Record(String fileName, String type, Long sucSeq) {
        this.delDate = new Timestamp(System.currentTimeMillis());
        this.fileName = fileName;
        this.type = type;
        this.result = "1";
        this.sucSeq = sucSeq;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Timestamp delDate) {
        this.delDate = delDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public Long getSucSeq() {
        return sucSeq;
    }

    public void setSucSeq(Long sucSeq) {
        this.sucSeq = sucSeq;
    }
}
