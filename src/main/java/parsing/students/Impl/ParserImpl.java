package parsing.students.Impl;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import parsing.students.Parser;
import parsing.students.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim0872 on 11.04.16.
 */
public class ParserImpl implements Parser {

/*
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
*/

    public static ArrayList<Student> getStudents (FileInputStream fis,int iname, int isurname, int iemail, int iphone,int iid )throws IOException{
        Workbook wb = new XSSFWorkbook(fis);
        ArrayList<Student> list = new ArrayList();

        String name;
        String surname;
        String email;
        String phone;
        String id;

        for (Row row: wb.getSheetAt(0)) {

            name = (getCellText(row.getCell(iname)));
            surname = (getCellText(row.getCell(isurname)));
            email = (getCellText(row.getCell(iemail)));
            id = (getCellText(row.getCell(iid)));
            phone = (getCellText(row.getCell(iphone)));

            list.add(new Student(name,surname,email,phone,id));
        }
        list.remove(0);
        list.trimToSize();
        fis.close();
        //System.out.println(converter(8.9175364142E9)+ "/n");
        return list;
    }



    public static String getCellText(Cell cell){
        String Result = "";
        long Res;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                Result = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Result = cell.getDateCellValue().toString();
                } else {
                    Res =(converter(cell.getNumericCellValue()));// Double.toString(cell.getNumericCellValue());
                    //System.out.println(Res);
                    Result = String.valueOf(converter(cell.getNumericCellValue()));
                    // Guava CharMatcher
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                Result = Boolean.toString(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                Result = (cell.getCellFormula());
                break;
            default:
                break;
        }
        return Result;
    }

    static long converter(double d){
        long lon;
        while(true){

            if (d%10 !=0) {
                lon = (long) d;
                break;
            } else
                d*=10;
        }return lon;
    }
}
