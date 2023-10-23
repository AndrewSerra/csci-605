package homework_8_inc;

import java.util.ArrayList;
import java.util.List;

public class MyList<T extends Comparable<T>> {
    private List<T> list = new ArrayList<>();

    public void add(T item) {
        list.add(item);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(T item : list) {
            sb.append(item).append(" ");
        }
        sb.append("/ ").append(list.size());
        return sb.toString();
    }
}
