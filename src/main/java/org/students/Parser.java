package org.students;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract interface Parser  {

    static ArrayList<Student> getStudents(FileInputStream fis, int iname, int isurname, int iemail, int iphone, int iid)throws IOException {
        ArrayList<Student> list = null;
        return  list;
    }
}
