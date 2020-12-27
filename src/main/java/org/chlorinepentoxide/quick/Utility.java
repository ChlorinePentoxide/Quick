package org.chlorinepentoxide.quick;

import java.util.Vector;

public class Utility {

    public static int getNumberOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static <T> Vector<T> toVector(T[] array) {
        Vector<T> vector = new Vector<>(1,1);
        for(T t:array) vector.addElement(t);
        return vector;
    }

    public static String[] toStringArray(Vector<String> vector) {
        String[] array = new String[vector.size()];
        for(int i=0;i<array.length;i++) array[i] = vector.elementAt(i);
        return array;
    }
}
