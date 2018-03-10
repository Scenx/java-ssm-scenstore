package com.taotao.controller;

import com.taotao.common.utils.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Scen
 * @date 2018/3/10 17:37
 */
public class FTPTest {
    @Test
    public void testFtpClient() throws IOException {
//        创建一个ftp客户端
        FTPClient ftpClient = new FTPClient();
//        创建ftp连接
        ftpClient.connect("192.168.1.3");
//        登录ftp服务器 user pwd
        ftpClient.login("pcftp", "pcftp");
//        上传文件
//        读取本地文件
        FileInputStream inputStream = new FileInputStream(new File("/Volumes/macdata/idea项目/imgserverdemo/out/production/imgserverdemo/hot2.gif"));
//        设置上传路径
        ftpClient.changeWorkingDirectory("/images");
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.storeFile(new String("草草草.jpg".getBytes("GBK"), "iso-8859-1"), inputStream);
//        关闭连接
        ftpClient.logout();
    }

    @Test
    public void testFtpUtil() throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("/Volumes/macdata/idea项目/imgserverdemo/out/production/imgserverdemo/hot2.gif"));
        FtpUtil.uploadFile("192.168.1.3", 21, "pcftp", "pcftp", "/images", "/2018/03/10", "test.jpf", inputStream);
    }
}
