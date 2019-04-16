package com.tt.msg.entity;


import java.sql.Timestamp;

/**
 * @ClassName Satellite
 * @Description 卫星报文信息
 * @Author tanjiang
 * @CreateTime 2019/4/12 19:53
 * @Version 1.0
 **/

public class Satellite {
    /**
     * 序列号
     */
    private Long seq;
    /**
     * 文件日期
     */
    private Timestamp fileDate;
    /**
     * 仰角号
     */
    private String elevation;
    /**
     * 产品号
     */
    private String productId;
    /**
     * 站号
     */
    private String stationId;

    /**
     * 文件最终存储路径
     */
    private String filePath;

    public Satellite() {
    }

    public Satellite(Timestamp fileDate, String elevation, String productId, String stationId, String filePath) {
        this.fileDate = fileDate;
        this.elevation = elevation;
        this.productId = productId;
        this.stationId = stationId;
        this.filePath = filePath;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Timestamp getFileDate() {
        return fileDate;
    }

    public void setFileDate(Timestamp fileDate) {
        this.fileDate = fileDate;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
