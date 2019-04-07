package com.tt.msg.entity;

import com.tt.msg.utils.DateString;


/**
 * @ClassName RecordInfo
 * @Description 用于展示记录的类
 * @Author tanjiang
 * @CreateTime 2019/4/5 10:21
 * @Version 1.0
 **/

public class RecordInfo {
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
    private String delDate;
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

    public RecordInfo() {
    }

    public RecordInfo(Record record) {
        this.seq = record.getSeq();
        this.fileName = record.getFileName();
        this.failMsg = record.getFailMsg();
        this.sucSeq = record.getSucSeq();
        this.delDate = DateString.getFullString(record.getDelDate());
        if("0".equals(record.getResult())){
            this.result = "失败";
        }else {
            this.result = "成功";
        }
        if("0".equals(record.getType())){
            this.type = "类型一";
        }else if("1".equals(record.getType())){
            this.type = "类型二";
        }else if ("2".equals(record.getType())){
            this.type = "类型三";
        }else {
            this.type = "该类型需要到RecordInfo拓展";
        }
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

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
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
