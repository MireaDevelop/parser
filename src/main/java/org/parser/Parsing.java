package parsing;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Parsing {

    public static ArrayList<String> parse(String name) {
        InputStream in;
        XSSFWorkbook wb = null;
        try {
            in = new FileInputStream(name);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<>();
        assert wb != null;
        XSSFSheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        list.add(cell.getStringCellValue());
                        break;
                }
            }
            // result += "\n";
        }

        return list;
    }

}
