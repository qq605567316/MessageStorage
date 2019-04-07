package com.tt.msg.entity;

/**
 * @ClassName Radar
 * @Description 雷达反演产品类
 * @Author tanjiang
 * @CreateTime 2019/4/1 9:22
 * @Version 1.0
 **/

public class Radar {
    /**
     * 序列号
     */
    private Long seq;
    /**
     * 生成的gdr文件路径
     */
    private String gdrFilePath;
    /**
     * 生成的grd文件路径
     */
    private String grdFilePath;

    public Radar() {
    }

    public Radar(Long seq, String gdrFilePath, String grdFilePath) {
        this.seq = seq;
        this.gdrFilePath = gdrFilePath;
        this.grdFilePath = grdFilePath;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getGdrFilePath() {
        return gdrFilePath;
    }

    public void setGdrFilePath(String gdrFilePath) {
        this.gdrFilePath = gdrFilePath;
    }

    public String getGrdFilePath() {
        return grdFilePath;
    }

    public void setGrdFilePath(String grdFilePath) {
        this.grdFilePath = grdFilePath;
    }
}
