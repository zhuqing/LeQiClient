/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.file;

import java.io.File;

/**
 *
 * @author duyi
 */
public class FileUtil {

    public static String getFileExt(File file) {
        String fileName = file.getName();
        String[] fileNames = fileName.split("\\.");
        return fileNames[fileNames.length - 1];
    }

}
