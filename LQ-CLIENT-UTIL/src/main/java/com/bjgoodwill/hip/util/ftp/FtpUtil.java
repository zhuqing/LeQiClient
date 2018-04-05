/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjgoodwill.hip.util.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

public class FtpUtil {

    private FTPClient ftpClient;

    private boolean login = false;

    public FtpUtil(String url) throws Exception {
        if (url == null || url.isEmpty()) {
            throw new Exception("FTP登陆失败！！！");
        }
        String[] urls = url.split(":");
        if (urls == null || urls.length < 4) {
            throw new Exception("FTP登陆失败！！！");
        }
        init(urls[0], Integer.parseInt(urls[1]), urls[2], urls[3]);
    }

    public FtpUtil(String ip, int port, String loginName, String loginPwd) throws Exception {
        init(ip, port, loginName, loginPwd);
    }

    private void init(String ip, int port, String loginName, String loginPwd) throws Exception {
        ftpClient = new FTPClient();
        ftpClient.connect(ip, port);
        login = ftpClient.login(loginName, loginPwd);
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.enterLocalPassiveMode();
        if (!login) {
            throw new Exception("FTP登陆失败！！！");
        }
    }

    public FTPFile getFile(String remotePath, String fileName) throws IOException {
        return getFile(remotePath + "/" + fileName);
    }

    public FTPFile getFile(String remoteFile) throws IOException {
        FTPListParseEngine engine = ftpClient.initiateListParsing(remoteFile);
        FTPFile[] ftpFiles = engine.getNext(100);
        if (ftpFiles.length > 0) {
            return ftpFiles[0];
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param remoteFileName
     * @param locaFileName
     * @return
     * @throws java.lang.Exception
     */
    public boolean download(String remoteFileName, String locaFileName) throws Exception {
        System.err.println("开始下载文件：" + locaFileName);
        OutputStream output = null;
        File loaclFile = new File(locaFileName);
        File file = loaclFile.getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        output = new FileOutputStream(locaFileName);
        boolean bool = ftpClient.retrieveFile(remoteFileName, output);

        output.close();
        if (!bool && loaclFile.exists()) {
            loaclFile.delete();
            System.err.println("文件检索不到该文件。");
        }
        System.err.println("下载完成:" + bool);
        return bool;
    }

    public InputStream getFileInputStream(String remoteFileName) throws IOException {

        return ftpClient.retrieveFileStream(remoteFileName);
    }

    public void close() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
        }
        ftpClient.disconnect();
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    /**
     * @param ftpFileMap 存放ftp上的文件的映射
     * @param remoteFile 起始文件路径
     * @return
     * @throws java.io.IOException
     */
    public Map<String, FTPFile> getFilesMap(String remoteFile, Map<String, FTPFile> ftpFileMap) throws IOException {
        FTPFile[] fTPFiles = ftpClient.listFiles(remoteFile);
        // ftpClient.retrieveFileStream("\\config\\HipAppClient.properties");
        for (FTPFile fTPFile : fTPFiles) {
            if (fTPFile.isDirectory()) {
                getFilesMap(remoteFile + "\\" + fTPFile.getName(), ftpFileMap);
            } else {
                ftpFileMap.put(remoteFile + "\\" + fTPFile.getName(), fTPFile);
            }
        }
        return ftpFileMap;
    }
}
