package me.kospo.javalang.generics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {
    public static<T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static <T> List<? extends T> limit(List<? extends T> source, int size) {
        List<T> ret = new ArrayList<>();

        for (T obj : source) {
            if(ret.size() < size) {
                ret.add(obj);
            } else {
                break;
            }
        }

        return ret;
    }

    public static <T> void add(List<? super T> destination, T o) {
        destination.add(o);
    }

    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> items) {
        removeFrom.removeAll(items);
    }

    public static <T> boolean containsAll(List<? super T> searchIn, List<? extends T> items) {
        return searchIn.containsAll(items);
    }

    public static <T> boolean containsAny(List<? super T> searchIn, List<? extends T> items) {
        for (T o : items) {
            if(searchIn.contains(o)) {
                return true;
            }
        }

        return false;
    }

    public static <T extends Comparable<? super T>> List<T> range(List<? extends T> list, T min, T max) {
        List<T> ret = new ArrayList<>();

        for (T obj : list) {
            if(obj.compareTo(min) >= 0 && obj.compareTo(max) < 0) {
                ret.add(obj);
            }
        }

        return ret;
    }

    public static <T> List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        List<T> ret = new ArrayList<>();

        for (T obj : list) {
            if(comparator.compare(obj, min) >= 0 && comparator.compare(obj, max) < 0) {
                ret.add(obj);
            }
        }

        return ret;
    }
}
