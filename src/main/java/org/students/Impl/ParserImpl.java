package org.students.Impl;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.students.Parser;
import org.students.Student;

import java.io.*;
import java.util.ArrayList;


public class ParserImpl implements Parser {

    private static int emailindex;
    private static int vkindex;
    private static int phoneindex;


    public static ArrayList<String> getTitles(String file) {

        Workbook wb = null;

        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {

            wb = new XSSFWorkbook(in);
        }
        catch (IOException e){

            e.printStackTrace();
        }

        ArrayList<String> list = new ArrayList();

        Row row = wb.getSheetAt(0).getRow(0);
        for (Cell cell : row){
            list.add(getCellText(cell));
        }
        list.remove(0);

        return list;
    }


    public static ArrayList<Student> getStudents (String file,int iname, int iemail, int iid,int iphone ) {

        emailindex = iemail;
        vkindex = iid;
        phoneindex = iphone;

        Workbook wb = null;

        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {

            wb = new XSSFWorkbook(in);
        }
        catch (IOException e){

            e.printStackTrace();
        }

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

        return list;
    }


    public static void log(String file, ArrayList<Student> list,boolean isSendVk, boolean isSendMail, boolean isSendPhone) {

        Workbook wb = null;

        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {

            wb = new XSSFWorkbook(in);

        }   catch (IOException e){

            e.printStackTrace();
        }

        CellStyle redstyle   = wb.createCellStyle();
        CellStyle greenstyle = wb.createCellStyle();

        redstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redstyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        greenstyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        greenstyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        int i = 1;

        for (Student student : list) {

            Row row = wb.getSheetAt(0).getRow(i);

            if ((student.getIsSendVk()) && (isSendVk)) {
                row.getCell(vkindex).setCellStyle(greenstyle);
            } else if ((!(student.getIsSendVk())) && (isSendVk)) {
                row.getCell(vkindex).setCellStyle(redstyle);
            }

            if ((student.getIsSendMail()) && (isSendMail)) {
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

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream("Результаты рассылки.xlsx"))) {

            wb.write(out);
            out.flush();

        } catch (IOException e){
            e.printStackTrace();
        }
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

        StringBuilder s = new StringBuilder("");

        for(int i = 0; i<phone.length();i++) {
            if (Character.isDigit(phone.charAt(i))) {

                s.append(phone.charAt(i));
            }
        }
        if ( (s.length()>0) && (s.charAt(0) == '8') && (s.length() == 11) ){

            s.setCharAt(0,'7');

        } else if ((s.length()>0) && (s.length() == 10) ) {

            s.insert(0,'7');
        }

        return s.toString();
    }

}
