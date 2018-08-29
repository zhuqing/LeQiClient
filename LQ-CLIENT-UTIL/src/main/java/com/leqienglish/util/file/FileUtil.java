/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.file;

import com.leqienglish.util.exception.LQExceptionUtil;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author zhuleqi
 */
public class FileUtil {

    public static String EN_WORD_TYPE = "en";

    public static String AM_WORD_TYPE = "am";

    public static String TTS_WORD_TYPE = "tts";

    public static String VERSION_ADNROID_TYPE = "android";
    public static String VERSION_IOS_TYPE = "ios";
    public static String VERSION_DES_TYPE = "desktop";

    private final static FileUtil fileUtil = new FileUtil();

    protected FileUtil() {

    }

    public static FileUtil getInstence() {
        return fileUtil;
    }

    public String getFileExt(File file) {
        String fileName = file.getName();
        String[] fileNames = fileName.split("\\.");
        return fileNames[fileNames.length - 1];
    }

    public static final String APP_NAME = "leqienglish";
    public static final String IMAGE = "image";
    public static final String AUDIO = "audio";
    public static final String WORD = "word";
    public static final String VERSION = "version";

    public String appRootPath() {

        String userDir = System.getProperties().getProperty("user.home");
        StringBuffer sb = new StringBuffer();
        sb.append(userDir).append(File.separatorChar).append(APP_NAME);
        String rootpath = sb.toString();
        initDirectory(rootpath);
        return rootpath;
    }

    public void initDirectory(String rootpath) {
        File file = new File(rootpath);
        if (!file.exists()) {
            synchronized ("initDir") {
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
    }

    /**
     * 相对路径转换为绝对路径
     *
     * @param path
     * @return
     */
    public String toLocalFilePath(String path) throws Exception {
        LQExceptionUtil.required(!(path == null || path.isEmpty()), "path 参数不能为空");

        path = path.replace('/', File.separatorChar);
        path = fileUtil.appRootPath() + File.separator + path;
        fileUtil.createDir(path);
        return path;
    }

    public void createDir(String filePath) {
        if (filePath.contains(".")) {
            int index = filePath.lastIndexOf(File.separator);
            filePath = filePath.substring(0, index);
            createDir(filePath);
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public boolean hasFileOrCreate(String filePath) throws IOException {

        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        createDir(filePath);
        file.createNewFile();
        return false;

    }

    public String fileName(String end) {
        StringBuffer randomName = new StringBuffer();
        synchronized ("fileName") {
            randomName.append(UUID.randomUUID().toString()).append("_").append(System.currentTimeMillis()).append(".").append(end);
        }

        return randomName.toString();
    }

    public String imageDirectory() {
        return createDirectory(IMAGE);
    }

    public String wordDirectory(String word) {
        StringBuffer sb = new StringBuffer();
        sb.append(appRootPath()).append(File.separatorChar).append(WORD).append(File.separatorChar).append(word);//
        initDirectory(sb.toString());

        sb = new StringBuffer();
        sb.append(WORD).append(File.separatorChar).append(word);
        return sb.toString();
    }

    public String versionRelationPath(String partName) {
        StringBuffer sb = new StringBuffer();
        sb.append(VERSION).append(File.separatorChar).append(this.date2DirName());
        initDirectory(this.appRootPath()+File.separator+sb.toString());
        sb.append(File.separatorChar).append(UUID.randomUUID().toString()).append("-").append(partName);
        return sb.toString();
    }

    public String wordFilelPath(String word, String type) {
        StringBuffer sb = new StringBuffer();
        sb.append(appRootPath()).append(File.separatorChar).append(WORD).append(File.separatorChar).append(word);//
        initDirectory(sb.toString());
        sb.append(File.separatorChar).append(word).append("_").append(type).append(".").append("mp3");
        return sb.toString();
    }

    public String createDirectory(String dirName) {
        StringBuffer sb = new StringBuffer();
        sb.append(dirName).append(File.separatorChar);
        sb.append(date2DirName());
        String imagePath = sb.toString();
        return imagePath;
    }

    public String audioDirectory() {

        return createDirectory(AUDIO);
    }

    public String date2DirName() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        sb.append(calendar.get(Calendar.YEAR));
        if (calendar.get(Calendar.MONTH) >= 10) {
            sb.append(calendar.get(Calendar.MONTH) + 1);
        } else {
            sb.append(0).append(calendar.get(Calendar.MONTH));
        }

        if (calendar.get(Calendar.DAY_OF_MONTH) >= 10) {
            sb.append(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            sb.append(0).append(calendar.get(Calendar.DAY_OF_MONTH));
        }

        return sb.toString();
    }

    /**
     * 通过文件后缀获取文件的路径
     *
     * @param fileSuffix
     * @return
     */
    public String getPathByFileSuffix(String fileSuffix) {
        switch (fileSuffix) {
            case "mp3":
                return fileUtil.audioDirectory();
            case "jpg":
                return fileUtil.imageDirectory();

        }

        return null;
    }

    /**
     * 写入文件
     *
     * @param file
     * @param fileSuffix
     * @return
     * @throws IOException
     */
    public String writeWordAudioFile(byte[] file, String word, String type, String fileSuffix) throws IOException {
        String path = wordDirectory(word);

        if (path == null) {
            return null;
        }

        String filePath = fileUtil.appRootPath() + File.separator + path + File.separator;
        String imageFileName = word + "_" + type + "." + fileSuffix;

        wirteFile(file, filePath, imageFileName);
        return path + File.separator + imageFileName;
    }

    /**
     * 写入文件
     *
     * @param file
     * @param fileSuffix
     * @return
     * @throws IOException
     */
    public void writeFileDirectly(byte[] fileBytes, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileCopyUtils.copy(fileBytes, file);
    }

    /**
     * 写入文件
     *
     * @param file
     * @param fileSuffix
     * @return
     * @throws IOException
     */
    public String writeFile(byte[] file, String fileSuffix) throws IOException {
        String path = getPathByFileSuffix(fileSuffix);

        if (path == null) {
            return null;
        }

        String filePath = fileUtil.appRootPath() + File.separator + path + File.separator;
        String imageFileName = fileUtil.fileName(fileSuffix);

        wirteFile(file, filePath, imageFileName);
        return path + File.separator + imageFileName;
    }

    private void wirteFile(byte[] file, String fileDir, String fileName) throws IOException {
        File uploadFile = new File(fileDir + File.separator + fileName);

        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!uploadFile.exists()) {
            uploadFile.createNewFile();
        }
        if (!uploadFile.exists()) {
            uploadFile.createNewFile();
        }
        FileCopyUtils.copy(file, uploadFile);

    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public boolean fileExit(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }
        File file = new File(path);

        return file.exists();
    }

}
