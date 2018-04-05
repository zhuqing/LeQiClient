/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjgoodwill.hip.util.file.excel;

import com.bjgoodwill.hip.util.file.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

/**
 * Excel工具类　TODO 数值、公式、Boolean、blank 格式化数据写入。
 *
 * @author duyi
 */
public class ExcelUtil {

    /**
     * 日志
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * excel文件类型
     *
     * @author duyi
     */
    public enum ExcelEnum {

        XLS, XLSX;
    }

    /**
     * 导出Excel文件
     *
     * @param workbook
     * @param path
     * @throws IOException
     */
    public static void expExcel(Workbook workbook, String path) throws IOException {
        if (workbook instanceof HSSFWorkbook && path != null && path.contains(".xls")) {
            expExcel((HSSFWorkbook) workbook, path);
        } else if (workbook instanceof XSSFWorkbook && path != null && path.contains(".xlsx")) {
            expExcel((XSSFWorkbook) workbook, path);
        } else {
            LOGGER.error("路径：" + path + "不合法！");
        }
    }

    /**
     * 读取Excel文件数据
     *
     * @param path
     * @param startRow
     * @return
     * @throws java.io.IOException
     */
    public static Map<String, Object[][]> readExcel(String path, Integer... startRow) throws IOException {
        File file = new File(path);
        return readExcel(file, startRow);
    }

    /**
     * 读取Excel文件数据
     *
     * @param file
     * @param startRow
     * @return
     * @throws java.io.IOException
     */
    public static Map<String, Object[][]> readExcel(File file, Integer... startRow) throws IOException {
        Map<String, Object[][]> result = null;
        if (file != null && "xls".equals(FileUtil.getFileExt(file))) {
            result = readXls(file, startRow);
            return result;
        } else if (file != null && "xlsx".equals(FileUtil.getFileExt(file))) {
            result = readXlsx(file, startRow);
            return result;
        } else {
            LOGGER.error("文件：" + file + "  path:" + file.getPath() + "不合法！");
            return result;
        }
    }

    /**
     * 将xls导出到指定路径
     *
     * @param workBook
     * @param path2003
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void expExcel(HSSFWorkbook workBook, String path2003) throws FileNotFoundException, IOException {
        File file = new File(path2003);
        FileOutputStream os = new FileOutputStream(file);
        workBook.write(os);// 将文档对象写入文件输出流
        os.close();// 关闭文件输出流
    }

    /**
     * 将xlsx导出到指定路径
     *
     * @param workBook
     * @param path2007
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void expExcel(XSSFWorkbook workBook, String path2007) throws FileNotFoundException, IOException {
        File file = new File(path2007);
        FileOutputStream os = new FileOutputStream(file);
        workBook.write(os);// 将文档对象写入文件输出流
        os.close();// 关闭文件输出流
    }

    public static Workbook createExcel(Map<String, Object[][]> objects, ExcelEnum excelEnum) {
        if (ExcelEnum.XLS.equals(excelEnum)) {
            return createXls(objects);
        }
        return createXlsx(objects);
    }

    /**
     * 创建xls文件
     *
     * @param objects
     * @return
     */
    private static HSSFWorkbook createXls(Map<String, Object[][]> objects) {
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFCellStyle sheetStyle = workBook.createCellStyle();// 创建样式对象
        for (Map.Entry<String, Object[][]> entry : objects.entrySet()) {
            createExcelSheet(entry.getValue(), sheetStyle, workBook, entry.getKey());
        }
        return workBook;
    }

    /**
     * 创建xlsx文件
     *
     * @param objects
     * @return
     */
    private static XSSFWorkbook createXlsx(Map<String, Object[][]> objects) {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFCellStyle sheetStyle = workBook.createCellStyle();// 创建样式对象
        for (Map.Entry<String, Object[][]> entry : objects.entrySet()) {
            createExcelSheet(entry.getValue(), sheetStyle, workBook, entry.getKey());
        }
        return workBook;
    }

    /**
     * 在Xls文件中创建Sheet
     *
     * @param objects
     * @param sheetStyle
     * @param workBook
     */
    private static void createExcelSheet(Object[][] objects, HSSFCellStyle sheetStyle, HSSFWorkbook workBook, String sheetName) {
        HSSFSheet sheet = workBook.createSheet(sheetName);// 创建一个工作薄对象
        createExcelRow(objects, sheet, sheetStyle, workBook);
    }

    /**
     * 在Xlsx文件中创建Sheet
     *
     * @param objects
     * @param sheetStyle
     * @param workBook
     */
    private static void createExcelSheet(Object[][] objects, XSSFCellStyle sheetStyle, XSSFWorkbook workBook, String sheetName) {
        XSSFSheet sheet = workBook.createSheet(sheetName);// 创建一个工作薄对象
        createExcelRow(objects, sheet, sheetStyle, workBook);
    }

    /**
     * 在Xlsx文件指定sheet中创建一行
     *
     * @param objects
     * @param sheet
     * @param sheetStyle
     * @param workBook
     */
    private static void createExcelRow(Object[][] objects, HSSFSheet sheet, HSSFCellStyle sheetStyle, HSSFWorkbook workBook) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                continue;
            }
            HSSFRow row = sheet.createRow(i);// 创建一个行对象
            HSSFCellStyle rowStyle = workBook.createCellStyle();
            rowStyle.cloneStyleFrom(sheetStyle);
//            HSSFCellStyle rowStyle = (HSSFCellStyle) sheetStyle.clone();
//            setRowStyleValue(rowStyle, row.getRowNum(), workBook);
            createExcelCell(objects[i], row, rowStyle, workBook);
        }

    }

    /**
     * 在Xlsx文件指定sheet中创建一行
     *
     * @param objects
     * @param sheet
     * @param sheetStyle
     * @param workBook
     */
    private static void createExcelRow(Object[][] objects, XSSFSheet sheet, XSSFCellStyle sheetStyle, XSSFWorkbook workBook) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                continue;
            }
            XSSFRow row = sheet.createRow(i);// 创建一个行对象
            XSSFCellStyle rowStyle = workBook.createCellStyle();
            rowStyle.cloneStyleFrom(sheetStyle);
//            XSSFCellStyle rowStyle = (XSSFCellStyle) sheetStyle.clone();
//            setRowStyleValue(rowStyle, row.getRowNum(), workBook);
            createExcelCell(objects[i], row, rowStyle, workBook);
        }

    }

    /**
     * 在Xls文件的指定行中创建一个单元格
     *
     * @param objects
     * @param row
     * @param rowStyle
     * @param workBook
     */
    private static void createExcelCell(Object[] objects, HSSFRow row, HSSFCellStyle rowStyle, HSSFWorkbook workBook) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                continue;
            }
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(objects[i].toString());// 写入数据
            HSSFCellStyle cellStyle = workBook.createCellStyle();
            cellStyle.cloneStyleFrom(rowStyle);
//            HSSFCellStyle cellStyle = (HSSFCellStyle) rowStyle.clone();
//            setCellStyleValue(cellStyle, row.getRowNum(), i, workBook);
            cell.setCellStyle(cellStyle);// 应用样式对象
        }
    }

    /**
     * 在Xlsx文件的指定行中创建一个单元格
     *
     * @param objects
     * @param row
     * @param rowStyle
     * @param workBook
     */
    private static void createExcelCell(Object[] objects, XSSFRow row, XSSFCellStyle rowStyle, XSSFWorkbook workBook) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                continue;
            }
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(objects[i].toString());// 写入数据
            XSSFCellStyle cellStyle = workBook.createCellStyle();
            cellStyle.cloneStyleFrom(rowStyle);
//            XSSFCellStyle cellStyle = (XSSFCellStyle) rowStyle.clone();
//            setCellStyleValue(cellStyle, row.getRowNum(), i, workBook);
            cell.setCellStyle(cellStyle);// 应用样式对象
        }
    }

    /**
     * 读取Xls文件数据
     *
     * @param file
     * @return
     * @throws java.io.FileNotFoundException
     */
    private static Map<String, Object[][]> readXls(File file, Integer... startRow) throws FileNotFoundException, IOException {
//        File file = new File(path);
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
        Map<String, Object[][]> result = readExcelWorkbook(hwb, startRow);
        return result;
    }

    /**
     * 读取Xlsx文件数据
     *
     * @param file
     * @return
     * @throws java.io.FileNotFoundException
     */
    private static Map<String, Object[][]> readXlsx(File file, Integer... startRow) throws FileNotFoundException, IOException {
//        File file = new File(path);
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
        Map<String, Object[][]> result = readExcelWorkbook(xwb, startRow);
        return result;
    }

    /**
     * 读取Xls文件数据
     *
     * @param inputStream
     * @param startRow
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Map<String, Object[][]> readXls(InputStream inputStream, Integer... startRow) throws FileNotFoundException, IOException {
//        File file = new File(path);
        HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
        Map<String, Object[][]> result = readExcelWorkbook(hwb, startRow);
        return result;
    }

    /**
     * 读取Xlsx文件数据
     *
     * @param inputStream
     * @param startRow
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Map<String, Object[][]> readXlsx(InputStream inputStream, Integer... startRow) throws FileNotFoundException, IOException {
//        File file = new File(path);
        XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
        Map<String, Object[][]> result = readExcelWorkbook(xwb, startRow);
        return result;
    }

    /**
     * 读取Xls文件中数据
     *
     * @param hwb
     * @return
     */
    private static Map<String, Object[][]> readExcelWorkbook(HSSFWorkbook hwb, Integer... startRow) {
        int sheetNum = hwb.getNumberOfSheets();
        Map<String, Object[][]> result = new HashMap<>();
        for (int i = 0; i < sheetNum; i++) {
            HSSFSheet sheet = hwb.getSheetAt(i);
            if (startRow == null || startRow.length < i + 1) {
                result.put(sheet.getSheetName(), readExcelSheet(sheet, 0));
                continue;
            }
            result.put(sheet.getSheetName(), readExcelSheet(sheet, startRow[i]));
        }
        return result;
    }

    /**
     * 读取Xlsx文件中数据
     *
     * @param xwb
     * @return
     */
    private static Map<String, Object[][]> readExcelWorkbook(XSSFWorkbook xwb, Integer... startRow) {
        int sheetNum = xwb.getNumberOfSheets();
        Map<String, Object[][]> result = new HashMap<>();
        for (int i = 0; i < sheetNum; i++) {
            XSSFSheet sheet = xwb.getSheetAt(i);
            if (startRow == null || startRow.length < i + 1) {
                result.put(sheet.getSheetName(), readExcelSheet(sheet, 0));
                continue;
            }
            result.put(sheet.getSheetName(), readExcelSheet(sheet, startRow[i]));
        }
        return result;
    }

    /**
     * 读取Xlsx文件的Sheet中数据
     *
     * @param sheet
     * @return
     */
    private static Object[][] readExcelSheet(XSSFSheet sheet, int startRow) {
        int start = startRow < sheet.getFirstRowNum() ? sheet.getFirstRowNum() : startRow;

        int end = sheet.getPhysicalNumberOfRows();
        XSSFRow row = null;
        if (end < 0) {
            return null;
        }
        Object[][] result = new Object[end][];
        for (int i = start; i < end; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            result[i] = readExcelRow(row);
        }
        return result;
    }

    /**
     * 读取Xls文件的Sheet中数据
     *
     * @param sheet
     * @return
     */
    private static Object[][] readExcelSheet(HSSFSheet sheet, int startRow) {
        int start = startRow < sheet.getFirstRowNum() ? sheet.getFirstRowNum() : startRow;

        int end = sheet.getPhysicalNumberOfRows();
        HSSFRow row = null;
        if (end < 0) {
            return null;
        }
        Object[][] result = new Object[end][];
        for (int i = start; i < end; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            result[i] = readExcelRow(row);
        }
        return result;
    }

    /**
     * 读取Xlsx文件中一行数据
     *
     * @param row
     * @return
     */
    private static Object[] readExcelRow(XSSFRow row) {
        int start = row.getFirstCellNum();
        int end = row.getLastCellNum();
        XSSFCell cell = null;
        if (end < 0) {
            return null;
        }
        Object[] object = new Object[end];
        for (int i = start; i < end; i++) {
            cell = row.getCell(i);
            if (cell == null) {
                continue;
            }
            object[i] = readExcelCell(cell);
        }
        return object;
    }

    /**
     * 读取Xls文件中一行数据
     *
     * @param row
     * @return
     */
    private static Object[] readExcelRow(HSSFRow row) {
        int start = row.getFirstCellNum();
        int end = row.getLastCellNum();
        HSSFCell cell = null;
        if (end < 0) {
            return null;
        }
        Object[] object = new Object[end];
        for (int i = start; i < end; i++) {
            cell = row.getCell(i);
            if (cell == null) {
                continue;
            }
            object[i] = readExcelCell(cell);
        }
        return object;
    }

    /**
     * 读取Xlsx文件中一个单元格的数据
     *
     * @param cell
     * @return
     */
    private static Object readExcelCell(XSSFCell cell) {
        Object value = null;

        DecimalFormat df = new DecimalFormat("0");// 格式化 number String
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
        DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字

        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING://字符串处理
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC://数值型处理 TODO 其他数值、日期格式化欠缺
                if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = nf.format(cell.getNumericCellValue());
                } else {
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                }
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN://Boolean型处理
                value = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_BLANK://Blank处理
                value = "";
                break;
            default://TODO 公式处理
                value = cell.toString();
        }
        return value;
    }

    /**
     * 读取Xls文件中一个单元格的数据
     *
     * @param cell
     * @return
     */
    private static Object readExcelCell(HSSFCell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");// 格式化 number String
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
        DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字

        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING://字符串处理
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC://数值型处理 TODO 其他数值、日期格式化欠缺
                if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = nf.format(cell.getNumericCellValue());
                } else {
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                }
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://Boolean型处理
                value = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK://Blank处理
                value = "";
                break;
            default://TODO 公式处理
                value = cell.toString();
        }
        return value;
    }

    /**
     * 从Xlsx中获取指定单元格样式 TODO 功能实现
     *
     * @param workBook
     * @param sheetNum
     * @param rowNum
     * @param columnNum
     * @return
     */
    public static XSSFCellStyle getCellStyleValue(XSSFWorkbook workBook, int sheetNum, int rowNum, int columnNum) {
        XSSFSheet sheet = workBook.getSheetAt(sheetNum);
        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cell = row.getCell(columnNum);
        return cell.getCellStyle();
    }

    /**
     * 从Xls中获取指定单元格样式 TODO 功能实现
     *
     * @param workBook
     * @param sheetNum
     * @param rowNum
     * @param columnNum
     * @return
     */
    public static HSSFCellStyle getCellStyleValue(HSSFWorkbook workBook, int sheetNum, int rowNum, int columnNum) {
        HSSFSheet sheet = workBook.getSheetAt(sheetNum);
        HSSFRow row = sheet.getRow(rowNum);
        HSSFCell cell = row.getCell(columnNum);
        return cell.getCellStyle();
    }

    /**
     * 构造一个Xlsx单元格的空样式 TODO 功能实现
     *
     * @param workBook
     * @return
     */
    public static XSSFCellStyle createNullStyle(XSSFWorkbook workBook) {
        return workBook.createCellStyle();
    }

    /**
     * 构造一个Xls单元格的空样式 TODO 功能实现
     *
     * @param workBook
     * @return
     */
    public static HSSFCellStyle createNullStyle(HSSFWorkbook workBook) {
        return workBook.createCellStyle();
    }

    /**
     * 配置单元格样式
     *
     * @param cellStyle
     * @param sheetNum
     * @param rowNum
     * @param columnNum
     * @param workBook
     */
    public static void setCellStyleValue(XSSFCellStyle cellStyle, int sheetNum, int rowNum, int columnNum, XSSFWorkbook workBook) {
        XSSFSheet sheet = workBook.getSheetAt(sheetNum);
        XSSFRow row = sheet.getRow(rowNum);
        row.getCell(columnNum).setCellStyle(cellStyle);
    }

    /**
     * 配置行样式
     *
     * @param rowStyle
     * @param sheetNum
     * @param rowNum
     * @param workBook
     */
    public static void setRowStyleValue(XSSFCellStyle rowStyle, int sheetNum, int rowNum, XSSFWorkbook workBook) {
        XSSFSheet sheet = workBook.getSheetAt(sheetNum);
        XSSFRow row = sheet.getRow(rowNum);
        row.setRowStyle(rowStyle);
    }

    /**
     * 配置列样式 TODO 空列不配置样式的实现
     *
     * @param columnStyle
     * @param sheetNum
     * @param columnNum
     * @param workBook
     */
    public static void setColumnStyleValue(XSSFCellStyle columnStyle, int sheetNum, int columnNum, XSSFWorkbook workBook) {
        XSSFSheet sheet = workBook.getSheetAt(sheetNum);
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            row.getCell(columnNum).setCellStyle(columnStyle);
        }
    }

    /**
     * 配置单元格样式
     *
     * @param cellStyle
     * @param sheetNum
     * @param rowNum
     * @param columnNum
     * @param workBook
     */
    public static void setCellStyleValue(HSSFCellStyle cellStyle, int sheetNum, int rowNum, int columnNum, HSSFWorkbook workBook) {
        HSSFSheet sheet = workBook.getSheetAt(sheetNum);
        HSSFRow row = sheet.getRow(rowNum);
        row.getCell(columnNum).setCellStyle(cellStyle);
    }

    /**
     * 配置行样式
     *
     * @param rowStyle
     * @param sheetNum
     * @param rowNum
     * @param workBook
     */
    public static void setRowStyleValue(HSSFCellStyle rowStyle, int sheetNum, int rowNum, HSSFWorkbook workBook) {
        HSSFSheet sheet = workBook.getSheetAt(sheetNum);
        HSSFRow row = sheet.getRow(rowNum);
        row.setRowStyle(rowStyle);
    }

    /**
     * 配置列样式 TODO 空列不配置样式的实现
     *
     * @param columnStyle
     * @param sheetNum
     * @param columnNum
     * @param workBook
     */
    public static void setColumnStyleValue(HSSFCellStyle columnStyle, int sheetNum, int columnNum, HSSFWorkbook workBook) {
        HSSFSheet sheet = workBook.getSheetAt(sheetNum);
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            HSSFRow row = sheet.getRow(i);
            row.getCell(columnNum).setCellStyle(columnStyle);
        }
    }

}
