package parsing;

import java.util.ArrayList;
import java.util.List;

public class Lists {
    private static List<String> names = new ArrayList<>();
    private static List<String> emails = new ArrayList<>();

    public static void SortList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if ((i + 1) % 2 == 0) {
                emails.add(list.get(i));
            } else {
                names.add(list.get(i));
            }
        }
    }

    public static List<String> getNames() {
        return names;
    }

    public static List<String> getEmails() {
        return emails;
    }
}