package parsing.students;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.IOException;

/**
 * Created by aleksejpluhin on 06.04.16.
 */
public interface ExcelWork {

    XSSFWorkbook getWorkbook(String filePath) throws IOException;


}
