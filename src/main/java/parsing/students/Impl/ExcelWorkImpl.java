package parsing.students.Impl;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import parsing.students.ExcelWork;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;


/**
 * Created by aleksejpluhin on 06.04.16.
 */
public class ExcelWorkImpl implements ExcelWork {
    private static final String REPORTS_DIRECTORY = System.getProperty("user.home") + "/reports";

    public ExcelWorkImpl(){

    }

    public XSSFWorkbook getWorkbook(String filePath)
    {
        FileInputStream fileInputStream = null;
        File file = new File(filePath);
        try {

            if(!file.exists()) {
                file.createNewFile();
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {

        }
        XSSFWorkbook workbook = null;
        try {
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {

        }

        return workbook;
    }
}
