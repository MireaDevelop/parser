package parsing.students.Impl;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import parsing.students.Parser;
import parsing.students.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksejpluhin on 06.04.16.
 */
public class ParserImpl implements Parser {

    List<Student> list = new ArrayList<Student>();
    XSSFWorkbook workbook;

    public ParserImpl(String filePath) throws IOException {
        workbook = new ExcelWorkImpl().getWorkbook(filePath);
    }

    public List<Student> getStudents() {
    for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
        XSSFSheet sheet = workbook.getSheetAt(i);
       try {
        for(int j = 1; j <= sheet.getLastRowNum(); j++) {
            String name = String.valueOf(sheet.getRow(j).getCell(0));
            String surname = String.valueOf(sheet.getRow(j).getCell(1));
            String email = String.valueOf(sheet.getRow(j).getCell(2));
            String phone = String.valueOf((sheet.getRow(j).getCell(3).getStringCellValue()));
            String id = String.valueOf(sheet.getRow(j).getCell(4).getStringCellValue()).replace("id", "");
            if (name.equals("null")) {
                break;
            }
            list.add(new Student(name, surname, email, phone, id));
        }
        } catch (NullPointerException e) {
        //  logger.debug("Пустая ячейка");
       }

    }
      //  logger.debug("список студентов обновлен");
        return list;

    }
}
