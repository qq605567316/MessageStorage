package com.tt.msg.job;

import com.tt.msg.entity.Radar;
import com.tt.msg.entity.Record;
import com.tt.msg.service.RadarService;
import com.tt.msg.service.RecordService;
import com.tt.msg.utils.ApplicationContextHelper;
import com.tt.msg.utils.DateString;
import com.tt.msg.utils.gentool.genRadarPic_gdr;
import com.tt.msg.utils.gentool.genRadarPic_grd;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @ClassName SecondJob
 * @Description 用于处理第二种类型报文 雷达产品报文
 * @Author tanjiang
 * @CreateTime 2019/2/25 22:05
 * @Version 1.0
 **/

public class SecondJob implements Job {

    private RadarService radarService = ApplicationContextHelper.getBean(RadarService.class);
    private RecordService recordService = ApplicationContextHelper.getBean(RecordService.class);

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    /**
     * 解析成功的路径
     */
    private final String successPath = File.separator + "type_two_success" + File.separator;

    /**
     * 解析失败的路径
     */
    private final String failPath = File.separator + "type_two_fail" + File.separator;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long timerSeq = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get("timerSeq");
        String filePath = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("filePath");
        File dir = new File(filePath);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return name.endsWith("zip");
            }
        };
        //获取所有同类文件
        File[] files = dir.listFiles(fileFilter);
        if (files == null) {
            return;
        }
        //每个文件单独处理
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            this.dealFile(file, timerSeq);
        }
    }

    /**
     * 具体的处理方法
     *
     * @param file
     */
    private void dealFile(File file, Long timerSeq) {
        //正在处理的源文件路径
        String filePath = file.getParent();
        //成功文件夹
        File successFile = new File(filePath + successPath + DateString.getFileDefaultString(new Date()) + "_" + file.getName());
        //获取处理文件成功时的路径
        String sucPath = successFile.getAbsolutePath();
        //存放图片返回结果的map
        HashMap<String, Object> retmap = new HashMap<String, Object>();
        //失败文件夹
        File failFile = new File(filePath + failPath + DateString.getFileDefaultString(new Date()) + "_" + file.getName());
        //获取处理文件失败时的路径
        String failPath = failFile.getAbsolutePath();

        String dealPath = "";

        try {
            //解压并返回解压后的路径
            dealPath = this.unZipFiles(file);
            //若解压失败
            if ("-1".equals(dealPath)) {
                //放入失败文件夹
                FileUtils.moveFile(file, failFile);
                //保存解压失败记录
                Record record = new Record(timerSeq, failPath, "1", "文件解压失败");
                recordService.insert(record);
                return;
            }
            //对解压出来的报文数据进行处理
            File dealDir = new File(dealPath);
            FileFilter filter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String name = pathname.getName();
                    return name.endsWith("gdr") || name.endsWith("grd");
                }
            };
            //过滤出grd与gdr文件
            File[] files = dealDir.listFiles(filter);
            //过滤非雷达产品zip
            if (files == null | files.length == 0) {
                //删除解压出来的文件
                dealAll(dealPath);
                return;
            }
            //每个文件单独处理
            for (File f : files) {
                if (f.getName().endsWith("gdr")) {
                    retmap = this.dealGDRFile(f);

                } else if (f.getName().endsWith("grd")) {
                    retmap = this.dealGRDFile(f);
                } else {
                    continue;
                }
            }
            /**
             * 返回的png处理标志码 1：成功 0：失败
             */
            Integer code = (Integer) retmap.get("RETCODE");
            //删除处理后不需要的文件
            if (code == 1) {
                Radar radar = dealEnding(dealPath);
                //插入数据表
                radarService.insert(radar);
                Long sucSeq = radar.getSeq();
                //放入记录表
                Record record = new Record(timerSeq, sucPath, "1", sucSeq);
                recordService.insert(record);
                //放入历史文件夹
                FileUtils.moveFile(file, successFile);
                LOGGER.info(file.getName() + "处理成功!");
            } else {
                dealAll(dealPath);
                Record record = new Record(timerSeq, failPath, "1", "处理成png图片时失败！");
                recordService.insert(record);
                //放入失败文件夹
                FileUtils.moveFile(file, failFile);
                LOGGER.info(file.getName() + "处理失败！");
                LOGGER.error(file.getName() + "处理失败,处理成png图片时失败,可能zip内文件数据不对！");
            }
        } catch (IOException e) {
            LOGGER.info(file.getName() + "处理失败！");
            dealAll(file.getAbsolutePath());
            Record record = new Record(timerSeq, failPath, "1", "移动失败文件时出现错误");
            recordService.insert(record);
            //放入失败文件夹
            try {
                FileUtils.moveFile(file, failFile);
                LOGGER.error(file.getName() + "处理失败：" + e.getMessage());
            } catch (IOException e1) {
                LOGGER.error(file.getName() + "移入fail文件夹失败：" + e.getMessage());
            }
        }

    }

    /**
     * 解压zip
     *
     * @param zipFile 待解压的文件
     * @return 解压出来的文件存放路径
     * @throws IOException
     */
    private String unZipFiles(File zipFile) {
        String filePath = zipFile.getAbsolutePath();
        String descDir = filePath.substring(0, filePath.lastIndexOf("."));
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        //解决zip文件中有中文目录或者中文文件
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile, Charset.forName("GBK"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                if (entry.isDirectory()) {
                    new File(descDir + File.separator + zipEntryName).mkdir();
                    continue;
                }
                InputStream in = zip.getInputStream(entry);
                String outPath = descDir + File.separator + zipEntryName;
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }

            return descDir;
        } catch (IOException e) {
            LOGGER.error("解压文件时发生错误，错误原因：" + e.getMessage());
            return "-1";
        } finally {
            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭zip文件流失败: " + e.getMessage());
            }
        }
    }


    /**
     * 处理gdr文件
     *
     * @param file
     */
    private HashMap<String, Object> dealGDRFile(File file) {
        HashMap<String, Object> retmap = new HashMap<String, Object>();
        genRadarPic_gdr genGDR = new genRadarPic_gdr();
        retmap = genGDR.drawRadarPNG(file.getParent(), file.getName());
        return retmap;
    }

    /**
     * 处理grd文件
     *
     * @param file
     */
    private HashMap<String, Object> dealGRDFile(File file) {
        HashMap<String, Object> retmap = new HashMap<String, Object>();
        genRadarPic_grd genGRD = new genRadarPic_grd();
        retmap = genGRD.drawRadarPNG(file.getParent(), file.getName());
        return retmap;
    }

    /**
     * 删除解压文件夹下非png文件 图片处理成功执行
     *
     * @param path
     */
    private Radar dealEnding(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        Radar radar = new Radar();
        for (File f : files) {
            String fileName = f.getName();
            if (!fileName.endsWith("png")) {
                f.delete();
            } else {
                if (fileName.contains("grd")) {
                    radar.setGrdFilePath(f.getAbsolutePath());
                }
                if (fileName.contains("gdr")) {
                    radar.setGdrFilePath(f.getAbsolutePath());
                }
            }
        }
        return radar;
    }

    /**
     * 删除解压文件夹 图片处理失败执行
     *
     * @param path
     */
    private void dealAll(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            } else {
                String[] filelist = file.list();
                if (filelist == null) {
                    return;
                }
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(path + File.separator + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else {
                        dealAll(path + File.separator + filelist[i]);
                    }
                }
                file.delete();
            }

        } catch (Exception e) {
            LOGGER.error("删除解压后的文件夹失败：" + e.getMessage());
        }
    }
}
