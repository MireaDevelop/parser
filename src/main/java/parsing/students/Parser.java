package parsing.students;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksejpluhin on 06.04.16.
 */
public abstract interface Parser  {

    static ArrayList<Student> getStudents(FileInputStream fis, int iname, int isurname, int iemail, int iphone, int iid)throws IOException{
        ArrayList<Student> list = null;
        return  list;
    }
}
