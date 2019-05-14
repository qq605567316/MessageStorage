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
     *
     * @param s
     * @return
     */
    public List<String> dealSurface(SurfaceObservation s) {
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

        String str;
        int num;

        //区站号
        String[] p1 = P1.split("\\s+");
        list.add(p1[0]);
        //观测时间
        str = DateString.Str2Date(p1[1]);
        list.add(str);
        //纬度
        str = p1[2].substring(0, 2) + "°" + p1[2].substring(2, 4) + "'" + p1[2].substring(4, 6) + "\"";
        list.add(str);
        //经度
        str = p1[3].substring(0, 3) + "°" + p1[3].substring(3, 5) + "'" + p1[3].substring(5, 7) + "\"";
        list.add(str);
        //观测方式
        if("1".equals(p1[6])){
            list.add("人工观测");
        }else{
            list.add("自动站观测");
        }
        //文件更正标志
        if("000".equals(p1[8])){
            list.add("是");
        }else {
            list.add("否");
        }

        String[] p2 = P2.split("\\s+");
        //本站气压
        num = Integer.valueOf(p2[1]);
        list.add(String.format("%.1f",num*0.1)+"hPa");
        //海平面气压
        num = Integer.valueOf(p2[2]);
        list.add(String.format("%.1f",num*0.1)+"hPa");
        //最高本站气压
        num = Integer.valueOf(p2[5]);
        list.add(String.format("%.1f",num*0.1)+"hPa");
        //最高本站气压出现时间
        list.add(p2[6].substring(0,2)+":"+p2[6].substring(2,4));
        //最低本站气压
        num = Integer.valueOf(p2[7]);
        list.add(String.format("%.1f",num*0.1)+"hPa");
        //最低本站气压出现时间
        list.add(p2[8].substring(0,2)+":"+p2[8].substring(2,4));

        String[] p3 = P3.split("\\s+");
        //气温
        num = Integer.valueOf(p3[1]);
        list.add(String.format("%.1f",100-num*0.1)+"℃");
        //最高气温
        num = Integer.valueOf(p3[2]);
        list.add(String.format("%.1f",100-num*0.1)+"℃");
        //最高气温出现时间
        list.add(p3[3].substring(0,2)+":"+p3[3].substring(2,4));
        //最低气温
        num = Integer.valueOf(p3[4]);
        list.add(String.format("%.1f",100-num*0.1)+"℃");
        //最低气温出现时间
        list.add(p3[5].substring(0,2)+":"+p3[5].substring(2,4));
        //露点温度
        num = Integer.valueOf(p3[9]);
        list.add(String.format("%.1f",100-num*0.1)+"℃");
        //相对湿度
        list.add(p3[10]+"%");
        //最小相对湿度
        list.add(p3[11]+"%");
        //最小相对湿度出现时间
        list.add(p3[12].substring(0,2)+":"+p3[12].substring(2,4));
        //水汽压
        num = Integer.valueOf(p3[13]);
        list.add(String.format("%.1f",num*0.1)+"hPa");

        String[] p4 = P4.split("\\s+");
        //小时降水量
        num = Integer.valueOf(p4[1]);
        list.add(String.format("%.1f",num*0.1)+"mm");

        String[] p5 = P5.split("\\s+");
        //两分钟风向
        list.add(p5[1]+"°");
        //两分钟平均风速
        num = Integer.valueOf(p5[2]);
        list.add(String.format("%.1f",num*0.1)+"m/s");
        //十分钟风向
        list.add(p5[3]+"°");
        //十分钟平均风速
        num = Integer.valueOf(p5[4]);
        list.add(String.format("%.1f",num*0.1)+"m/s");
        //最大风速风向
        list.add(p5[5]+"°");
        //最大风速
        num = Integer.valueOf(p5[6]);
        list.add(String.format("%.1f",num*0.1)+"m/s");
        //最大风速出现时间
        list.add(p5[7].substring(0,2)+":"+p5[7].substring(2,4));
        //瞬时风向
        list.add(p5[8]+"°");
        //瞬时风速
        num = Integer.valueOf(p5[9]);
        list.add(String.format("%.1f",num*0.1)+"m/s");

        String[] p6 = P6.split("\\s+");
        //地表温度
        num = Integer.valueOf(p6[1]);
        list.add(String.format("%.1f",100-num*0.1)+"℃");
        //地表最高温度
        num = Integer.valueOf(p6[2]);
        list.add(String.format("%.1f",100-num*0.1)+"℃");
        //地表最高温度出现时间
        list.add(p6[3].substring(0,2)+":"+p6[3].substring(2,4));
        //地表最低温度
        num = Integer.valueOf(p6[4]);
        list.add(String.format("%.1f",100-num*0.1)+"℃");
        //地表最低温度出现时间
        list.add(p6[5].substring(0,2)+":"+p6[5].substring(2,4));

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
