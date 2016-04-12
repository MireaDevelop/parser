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

    public static ArrayList<Student> getStudents (FileInputStream fis,int iname, int isurname, int iemail, int iid,int iphone )throws IOException{
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
            phone = phoneconvert(getCellText(row.getCell(iphone)));
            System.out.println(phone);
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
                    Result = String.valueOf( (long)(cell.getNumericCellValue()));
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
                d*=10;
            } else{
                lon = (long) d;
                break;
            }

        }return lon;
    }

    static String phoneconvert(String phone){
        String s = "";
        int i;
        for(i = 0; i<phone.length();i++) {
            if (Character.isDigit(phone.charAt(i))) {
                s = s + phone.charAt(i);
            }
        }
            /*if ((s.charAt(0) ==(char) 8) & (s.length() == 11) ) {
                String str = "7";
                for (i = 1; i<s.length();i++){
                    str = str + s.charAt(i);
                }
                s = str;
            }*/
              if (s.length() == 10 ) {
                s = 7 + s;
            }
        return s;
    }
}
