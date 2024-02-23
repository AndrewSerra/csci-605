package homework_8_inc;

import java.util.Collections;
import java.util.List;

public class MyCollections {

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        if((list == null) || list.size() <= 1) {
            return;
        }

        for (int i = 0; i < list.toArray().length; i++) {
            T min = list.get(i);
            for(int j = i; j < list.toArray().length; j++) {
                if(list.get(j).compareTo(min) < 0) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }

    public static <T> void sort(List<T> list, MyComparator<? super T> c) {
        if((list == null) || list.size() <= 1) {
            return;
        }

        for (int i = 0; i < list.toArray().length; i++) {
            T min = list.get(i);
            for(int j = i; j < list.toArray().length; j++) {
                if((c.compare(list.get(j), min) > 0)) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }
}
