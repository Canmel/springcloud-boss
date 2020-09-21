package com.camel.survey.utils;

import com.camel.survey.model.ZsCdrinfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FileUtils {
    @Value("${cti.record-file.url}")
    public String BASE_FILE_PATH;

    private static FileUtils fileUtils = new FileUtils();

    public static FileUtils getInstance() {
        return fileUtils;
    }

    /**
     * 把文件打成压缩包并输出到客户端浏览器中
     */
    public void downloadZipFiles(HttpServletResponse response, List<ZsCdrinfo> srcFiles, String zipFileName) {
        try {
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/octet-stream"); // 不同类型的文件对应不同的MIME类型 // 重点突出
            // 对文件名进行编码处理中文问题
            zipFileName = new String(zipFileName.getBytes("UTF-8"), "ISO-8859-1");
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName + ".zip");

            // --设置成这样可以不用保存在本地，再输出， 通过response流输出,直接输出到客户端浏览器中。
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            zipFile(srcFiles, zos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件
     *
     * @param filePaths 需要压缩的文件路径集合
     * @throws IOException
     */
    private void zipFile(List<ZsCdrinfo> filePaths, ZipOutputStream zos) {
        //设置读取数据缓存大小
        byte[] buffer = new byte[4096];
        try {
            //循环读取文件路径集合，获取每一个文件的路径
            for (ZsCdrinfo cdrinfo : filePaths) {
                InputStream inputFile = getInputStreamFromNetFileByUrl(BASE_FILE_PATH + cdrinfo.getRecordFile() + "&showname=1");
                BufferedInputStream bis = new BufferedInputStream(inputFile);
                //将文件写入zip内，即将文件进行打包
                zos.putNextEntry(new ZipEntry(cdrinfo.loadRecordFileName() + ".mp3"));
                //写入文件的方法，同上
                int size = 0;
                //设置读取数据缓存大小
                while ((size = bis.read(buffer)) > 0) {
                    zos.write(buffer, 0, size);
                }
                //关闭输入输出流
                zos.closeEntry();
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != zos) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
      * 根据地址获得数据的字节流
      *
      * @param strUrl
      *            网络连接地址
      * @return
      */
    public static InputStream getInputStreamFromNetFileByUrl(String strUrl) {
        try{
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            return conn.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
