package com.tt.msg.utils.gentool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 根据雷达反演产品生成图片
 */

public class genRadarPic_gdr {

    String _picType = null;
    private double _minX = 0;
    private double _minY = 0;
    private double _maxX = 0;
    private double _maxY = 0;
    /**
     * 图片大小
     */
    private int _width, _height;

    double[][] _gridData = null;

    private Color[] _colorset = null;
    private double[] _minvalue = null;
    private double[] _maxvalue = null;

    public genRadarPic_gdr(double[][] griddata, double startX, double startY, double endX, double endY, int width, int height) {
        this._gridData = griddata;
        this._maxX = endX;
        this._maxY = endY;
        this._minX = startX;
        this._minY = startY;
        this._width = width;
        this._height = height;
    }

    public genRadarPic_gdr() {
        Color[] colors = new Color[6];
        double[] min = new double[6];
        double[] max = new double[6];

        colors[5] = new Color(128, 0, 0);
        colors[4] = new Color(255, 0, 0);
        colors[3] = new Color(255, 255, 0);
        colors[2] = new Color(0, 180, 170);
        colors[1] = new Color(0, 255, 90);
        colors[0] = new Color(255, 255, 255);

        min[5] = 60;
        min[4] = 40;
        min[3] = 20;
        min[2] = 10;
        min[1] = 0.1;
        min[0] = -10;

        max[5] = 1000;
        max[4] = 60;
        max[3] = 40;
        max[2] = 20;
        max[1] = 10;
        max[0] = 0.1;

        this._maxvalue = max;
        this._minvalue = min;
        this._colorset = colors;
    }

    public HashMap<String, Object> drawRadarPNG(String filepath, String filename) {
        HashMap<String, Object> retmap = new HashMap<String, Object>();
        InputStream in = null;
        BufferedReader br = null;
        try {
            File f = new File(filepath + File.separator + filename);
            InputStreamReader insReader = new InputStreamReader(
                    new FileInputStream(f));
            br = new BufferedReader(insReader);

            List<String[]> ArrCSV = new ArrayList<String[]>();
            String inLine = null;
            int linenum = 1;
            int gridrow = 0;
            double[][] gridData = null;
            while ((inLine = br.readLine()) != null) {
                if (linenum == 1) {
                    _picType = inLine.trim();
                } else if (linenum == 2) {
                    String[] ss = inLine.split(",");
                    _maxX = Double.parseDouble(ss[0].trim());
                    _minY = Double.parseDouble(ss[1].trim());
                } else if (linenum == 4) {
                    String[] ss = inLine.split(",");
                    _minX = Double.parseDouble(ss[0].trim());
                    _maxY = Double.parseDouble(ss[1].trim());
                } else if (linenum == 5) {
                    String[] ss = inLine.split(",");
                    _width = Integer.parseInt(ss[0].trim());
                    _height = Integer.parseInt(ss[1].trim());
                    _gridData = new double[_width][_height];
                    gridData = new double[_height][_width];
                } else if (linenum > 5) {
                    // 开始初始化格点数据
                    String[] ss = inLine.split(" ");
                    for (int col = 0; col < ss.length; col++) {
                        double d = Double.parseDouble(ss[col].trim());
                        gridData[gridrow][col] = d / 100;
                    }
                    gridrow++;
                }
                linenum++;
            }
            // 旋转矩阵
            for (int r = 0; r < _width; r++) {
                for (int c = 0; c < _height; c++) {
                    _gridData[r][c] = gridData[c][r];
                }
            }

            String retstr = drawImage(filepath, filename);

            if (retstr.equals("s")) {
                retmap.put("RETCODE", 1);
                retmap.put("RETMSG", "success");
            } else {
                retmap.put("RETCODE", 0);
                retmap.put("RETMSG", retstr);
            }

        } catch (Exception ex) {
            retmap.put("RETCODE", 0);
            retmap.put("RETMSG", ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }

                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return retmap;
        }

    }

    Color findcolor(double value) {
        Color c = null;
        for (int i = 0; i < _minvalue.length; i++) {
            if ((_minvalue[i] <= value) && (_maxvalue[i] > value)) {
                c = _colorset[i];
                break;
            }
        }
        return c;
    }

    String drawImage(String filepath, String filename) throws Exception{
        BufferedImage bi = new BufferedImage(_width, _height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bi.createGraphics();

        bi = g2d.getDeviceConfiguration().createCompatibleImage(_width,
                _height, Transparency.TRANSLUCENT);

        for (int row = 0; row < _width; row++) {
            for (int col = 0; col < _height; col++) {
                double val = _gridData[row][col];
                Color c = findcolor(val);
                if (c != null) {
                    if (c.getRGB() != (Color.WHITE).getRGB()) {
                        bi.setRGB(row, col, c.getRGB());
                    }
                }
            }
        }

        g2d.dispose();

        filename = filename.replace(".","_");
        // 保存文件
        OutputStream out = new FileOutputStream(new File(filepath
                + File.separator + filename + ".png"));
        ImageIO.write(bi, "png", out);
        out.close();
        bi.flush();
        return "s";
    }

    public void setColor(Color[] colors) {
        this._colorset = colors;
    }

    public void setMinValue(double[] minvalue) {
        this._minvalue = minvalue;
    }

    public void setMaxValue(double[] maxvalue) {
        this._maxvalue = maxvalue;
    }

    public void setMinX(double minX) {
        this._minX = minX;
    }

    public double getMinX() {
        return this._minX;
    }

    public void setMaxX(double maxX) {
        this._maxX = maxX;
    }

    public double getMaxX() {
        return this._maxX;
    }

    public void setMinY(double minY) {
        this._minY = minY;
    }

    public double getMinY() {
        return this._minY;
    }

    public void setMaxY(double maxY) {
        this._maxY = maxY;
    }

    public double getMaxY() {
        return this._maxY;
    }

    public void setWidth(int width) {
        this._width = width;
    }

    public double getWidth() {
        return this._width;
    }

    public void setHeight(int height) {
        this._height = height;
    }

    public double getHeight() {
        return this._height;
    }
}
