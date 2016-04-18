package parsing.students.Impl;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import parsing.students.Parser;
import parsing.students.Student;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.IndexedColors.ORANGE;
import static org.apache.poi.ss.usermodel.IndexedColors.RED;

/**
 * Created by vadim0872 on 11.04.16.
 */
public class ParserImpl implements Parser {
         static int emailindex;
         static int vkindex;
         static int phoneindex;


    public static ArrayList<Student> getStudents (String file,int iname, int iemail, int iid,int iphone )throws IOException{

        emailindex = iemail;
        vkindex = iid;
        phoneindex = iphone;

        FileInputStream fis = new FileInputStream(file);

        Workbook wb = new XSSFWorkbook(fis);
        ArrayList<Student> list = new ArrayList();


        String name;
        String email;
        String phone;
        String id;

        for (Row row: wb.getSheetAt(0)) {
                name = (getCellText(row.getCell(iname)));
                email = (getCellText(row.getCell(iemail)));
                id = (getCellText(row.getCell(iid)));
                phone = phoneconvert(getCellText(row.getCell(iphone)));

                list.add(new Student(name, email, phone, id));
        }
        list.remove(0);
        list.trimToSize();



        fis.close();
        return list;
    }


    public static ArrayList<String> getTitles(String file) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(fis);
        ArrayList<String> list = new ArrayList();

        Row row = wb.getSheetAt(0).getRow(0);
        for (Cell cell : row){
            list.add(getCellText(cell));
        }
        list.remove(0);

        fis.close();
        return list;
    }

    public static void log(String file, ArrayList<Student> list,boolean isSendVk, boolean isSendMail, boolean isSendPhone) throws IOException {

        FileInputStream fis = new FileInputStream(file);

        Workbook wb = new XSSFWorkbook(fis);


        CellStyle redstyle = wb.createCellStyle();
        CellStyle greenstyle = wb.createCellStyle();
        redstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redstyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        greenstyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        greenstyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        int i = 1;

        for (Student student : list){

            Row row = wb.getSheetAt(0).getRow(i);

            if ((student.getIsSendVk()) &&(isSendVk)) {
                row.getCell(vkindex).setCellStyle(greenstyle);
            } else if ((!(student.getIsSendVk())) && (isSendVk)){
                row.getCell(vkindex).setCellStyle(redstyle);
            }

            if ((student.getIsSendMail()) && (isSendMail)){
                row.getCell(emailindex).setCellStyle(greenstyle);
            } else if ((!(student.getIsSendMail())) && (isSendMail)) {
                row.getCell(emailindex).setCellStyle(redstyle);
            }

            if ((student.getIsSendPhone()) && (isSendPhone)) {
                row.getCell(phoneindex).setCellStyle(greenstyle);
            } else if ((!(student.getIsSendPhone())) && (isSendPhone)) {
                row.getCell(phoneindex).setCellStyle(redstyle);
            }
            i++;
        }

        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        fos.flush();
        fos.close();
        fis.close();
    }



    public static String getCellText(Cell cell){
        String Result = "";
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

    static String phoneconvert(String phone){
        String s = "";
        int i;
        for(i = 0; i<phone.length();i++) {
            if (Character.isDigit(phone.charAt(i))) {
                s = s + phone.charAt(i);
            }
        }
            if ( (s.length()>0)&& (s.charAt(0) == '8') && (s.length() == 11) ) {

                String str = "7";
                for (i = 1; i<s.length();i++){

                    str = str + s.charAt(i);
                }
                s = str;
            } else if ((s.length()>0)&&(s.length() == 10) ) {
                s = 7 + s;
            }
        return s;
    }
}
