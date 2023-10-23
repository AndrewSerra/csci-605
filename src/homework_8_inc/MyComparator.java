package homework_8_inc;

import java.util.Comparator;

public class MyComparator<T extends Comparable<? super T>> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }

    @Override
    public Comparator<T> reversed() {
        return Comparator.super.reversed();
    }
}
