package parsing;

import java.util.Comparator;

public class MetroNameComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return Integer.compare(Integer.parseInt(o1, 10), Integer.parseInt(o2, 10));
    }
}
