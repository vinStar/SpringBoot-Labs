package cn.iocoder.springboot.lab72.utils;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

/**
 * @Classname POIUtil
 * @Description TODO
 * @Date 2020/3/20 22:08
 * @Created by mengdesheng
 */
@Log4j2
public class POIUtil {

    /**
     * 判断是否office文件
     * @param inputStream
     * @return
     */
    public static Boolean isOfficeFile(InputStream inputStream){
        boolean result = false;
        try {
            FileMagic fileMagic = FileMagic.valueOf(inputStream);
            if (Objects.equals(fileMagic,FileMagic.OLE2)||Objects.equals(fileMagic,fileMagic.OOXML)){
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否office文件
     * @param file
     * @return
     * @throws IOException
     */
    public static Boolean isOfficeFile(MultipartFile file) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
        boolean result = false;
        result = isOfficeFile(bufferedInputStream);
        return result;
    }

    /**
     * 判断扩展名是否是excel扩展名
     * @param extension
     * @return
     */
    public static Boolean checkExtension(String extension){
        return Lists.newArrayList("xls","xlsx","XLS","XLSX").contains(extension);
    }

    /**
     * 判断扩展名是否是excel扩展名
     * @param file
     * @return
     */
    public static Boolean checkExtension(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        return checkExtension(extension);
    }

    /**
     * 判断扩展名是否是CSV扩展名
     * @param file
     * @return
     */
    public static Boolean checkExtensionCSV(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        return Lists.newArrayList("csv","CSV").contains(extension);
    }

    /**
     * 自动判断文件类型
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbookAuto(MultipartFile file) throws IOException {
        /** 判断文件的类型，是2003还是2007 */
        boolean isExcel2003 = true;
        if (isExcel2007(file.getOriginalFilename())) {
            isExcel2003 = false;
        }
        BufferedInputStream is = new BufferedInputStream(
                file.getInputStream());
        Workbook wb;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        return wb;
    }



    /**
     * 自动判断文件类型
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbookAuto(File file) throws IOException {
        /** 判断文件的类型，是2003还是2007 */
        boolean isExcel2003 = false;

        BufferedInputStream is = new BufferedInputStream(
                new FileInputStream(file));
        Workbook wb;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        return wb;
    }




    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
