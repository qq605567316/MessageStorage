package com.tt.msg.utils.gentool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.imageio.stream.ImageOutputStream;

/**
 * 根据雷达反演产品生成图片
 */

public class genRadarPic_grd {

	String _picType = null;
	private double _minX = 0;
	private double _minY = 0;
	private double _maxX = 0;
	private double _maxY = 0;
	/**
	 * // 图片大小
	 */
	private int _width, _height;

	double[][] _gridData = null;

	private Color[] _colorset = null;
	private double[] _minvalue = null;
	private double[] _maxvalue = null;

	public genRadarPic_grd() {
		Color[] colors = new Color[15];
		double[] min = new double[15];
		double[] max = new double[15];

		min[0] = 70;
		min[1] = 65;
		min[2] = 60;
		min[3] = 55;
		min[4] = 50;
		min[5] = 45;
		min[6] = 40;
		min[7] = 35;
		min[8] = 30;
		min[9] = 25;
		min[10] = 20;
		min[11] = 15;
		min[12] = 10;
		min[13] = 5;
		min[14] = -10;

		max[0] = 2000;
		max[1] = 69.99;
		max[2] = 64.99;
		max[3] = 59.99;
		max[4] = 54.99;
		max[5] = 49.99;
		max[6] = 44.99;
		max[7] = 39.99;
		max[8] = 34.99;
		max[9] = 29.99;
		max[10] = 24.99;
		max[11] = 19.99;
		max[12] = 14.99;
		max[13] = 9.99;
		max[14] = 4.99;
		colors[0] = new Color(200, 100, 155);
		colors[1] = new Color(150, 0, 180);
		colors[2] = new Color(255, 180, 180);
		colors[3] = new Color(255, 100, 100);
		colors[4] = new Color(255, 0, 0);
		colors[5] = new Color(238, 255, 0);
		colors[6] = new Color(255, 255, 0);
		colors[7] = new Color(196, 166, 0);
		colors[8] = new Color(180, 255, 180);
		colors[9] = new Color(0, 220, 0);
		colors[10] = new Color(0, 150, 50);
		colors[11] = new Color(111, 248, 255);
		colors[12] = new Color(0, 186, 253);
		colors[13] = new Color(0, 107, 253);
		colors[14] = new Color(255, 255, 255);
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
				} else if (linenum > 5 && f.getName().endsWith(".grd")) {
					// 开始初始化格点数据
					String[] ss = inLine.split(" ");
					for (int col = 0; col < ss.length; col++) {
						double d = Double.parseDouble(ss[col].trim());
						gridData[gridrow][col] = d / 2 - 32;
					}
					gridrow++;
				} else if (linenum > 5 && f.getName().endsWith(".gdr")) {
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
			ex.printStackTrace();
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
		}

		return retmap;
	}

	String drawImage(String filepath, String filename) {
		try {
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
					// }
				}
			}

			g2d.dispose();

			// 保存文件
			OutputStream out = new FileOutputStream(new File(filepath
					+ File.separator + filename + ".png"));
			ImageIO.write(bi, "png", out);
			out.close();
			bi.flush();
			return "s";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
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
