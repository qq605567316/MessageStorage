package com.tt.msg.entity;

import com.tt.msg.utils.DateString;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 处理SurfaceObservation得到部分实际数据
     * @param s
     * @return
     */
    public List<String> dealSurface(SurfaceObservation s){
        List<String> list = new ArrayList<String>();
        String P1 = s.getSi();
        String P2 = s.getPp();
        String P3 = s.getTh();
        String P4 = s.getRe();
        String P5 = s.getWi();
        String P6 = s.getDt();
        String P7 = s.getVv();
        String P8 = s.getCw();
        String P9 = s.getSp();
        String P10 = s.getMr();
        String P11 = s.getMw();
        String P12 = s.getQc();
        String P13 = s.getQ1();
        String P14 = s.getQ2();
        String P15 = s.getQ3();

        String str = "";

        String[] p1 = P1.split("\\s+");
        list.add(p1[0]);

        str = DateString.Str2Date(p1[1]);
        list.add(str);
        str = p1[2].substring(0,2)+"°"+p1[2].substring(2,4)+"'"+p1[2].substring(4,6)+"\"";
        list.add(str);
        str = p1[3].substring(0,3)+"°"+p1[3].substring(3,5)+"'"+p1[3].substring(5,7)+"\"";
        list.add(str);
        String s1 = str.substring(0,1);
        if("-".equals(s1)){

        }
        list.add(p1[4]);

        String[] p2 = P2.split("\\s+");
        String[] p3 = P3.split("\\s+");
        String[] p4 = P4.split("\\s+");
        String[] p5 = P5.split("\\s+");
        String[] p6 = P6.split("\\s+");
        String[] p7 = P7.split("\\s+");
        String[] p8 = P8.split("\\s+");
        String[] p9 = P9.split("\\s+");
        String[] p10 = P10.split("\\s+");
        String[] p11 = P11.split("\\s+");
        String[] p12 = P12.split("\\s+");
        String[] p13 = P13.split("\\s+");
        String[] p14 = P14.split("\\s+");
        String[] p15 = P15.split("\\s+");


        return list;
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
