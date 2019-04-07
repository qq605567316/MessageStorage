package com.tt.msg.entity;

/**
 * @ClassName SurfaceObservation
 * @Description 地面观测数据纪录类
 * @Author tanjiang
 * @CreateTime 2019/3/2 10:14
 * @Version 1.0
 **/

public class SurfaceObservation {
    /**
     * 序列号
     */
    private Long seq;
    /**
     * 测站基本信息段
     */
    private String Si;
    /**
     * 气压数据
     */
    private String Pp;
    /**
     * 温度和湿度数据
     */
    private String Th;
    /**
     * 累计降水和蒸发量数据
     */
    private String Re;
    /**
     * 风观测数据
     */
    private String Wi;
    /**
     * 地温数据
     */
    private String Dt;
    /**
     * 自动观测能见度数据
     */
    private String Vv;
    /**
     * 人工观测能见度、云、天数据
     */
    private String Cw;
    /**
     * 其它重要天气数据
     */
    private String Sp;
    /**
     * 小时内每分钟降水量数据
     */
    private String Mr;
    /**
     * 人工观测连续天气现象
     */
    private String Mw;
    /**
     * 数据质量控制码标识符
     */
    private String Qc;
    /**
     * 数据质量控制码_台站级
     */
    private String Q1;
    /**
     * 数据质量控制码_省级
     */
    private String Q2;
    /**
     * 数据质量控制码_国家级
     */
    private String Q3;

    public SurfaceObservation() {
    }

    @Override
    public String toString() {
        return "SurfaceObservation{" +
                "seq=" + seq +
                ", Si='" + Si + '\'' +
                ", Pp='" + Pp + '\'' +
                ", Th='" + Th + '\'' +
                ", Re='" + Re + '\'' +
                ", Wi='" + Wi + '\'' +
                ", Dt='" + Dt + '\'' +
                ", Vv='" + Vv + '\'' +
                ", Cw='" + Cw + '\'' +
                ", Sp='" + Sp + '\'' +
                ", Mr='" + Mr + '\'' +
                ", Mw='" + Mw + '\'' +
                ", Qc='" + Qc + '\'' +
                ", Q1='" + Q1 + '\'' +
                ", Q2='" + Q2 + '\'' +
                ", Q3='" + Q3 + '\'' +
                '}';
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getSi() {
        return Si;
    }

    public void setSi(String si) {
        Si = si;
    }

    public String getPp() {
        return Pp;
    }

    public void setPp(String pp) {
        Pp = pp;
    }

    public String getTh() {
        return Th;
    }

    public void setTh(String th) {
        Th = th;
    }

    public String getRe() {
        return Re;
    }

    public void setRe(String re) {
        Re = re;
    }

    public String getWi() {
        return Wi;
    }

    public void setWi(String wi) {
        Wi = wi;
    }

    public String getDt() {
        return Dt;
    }

    public void setDt(String dt) {
        Dt = dt;
    }

    public String getVv() {
        return Vv;
    }

    public void setVv(String vv) {
        Vv = vv;
    }

    public String getCw() {
        return Cw;
    }

    public void setCw(String cw) {
        Cw = cw;
    }

    public String getSp() {
        return Sp;
    }

    public void setSp(String sp) {
        Sp = sp;
    }

    public String getMr() {
        return Mr;
    }

    public void setMr(String mr) {
        Mr = mr;
    }

    public String getMw() {
        return Mw;
    }

    public void setMw(String mw) {
        Mw = mw;
    }

    public String getQc() {
        return Qc;
    }

    public void setQc(String qc) {
        Qc = qc;
    }

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String q2) {
        Q2 = q2;
    }

    public String getQ3() {
        return Q3;
    }

    public void setQ3(String q3) {
        Q3 = q3;
    }
}
