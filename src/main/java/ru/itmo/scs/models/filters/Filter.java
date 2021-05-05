package ru.itmo.scs.models.filters;

import java.util.ArrayList;

public interface Filter<T> {
    ArrayList<T> applyFilter(ArrayList<T> inputCollection);

    default boolean isFiltered(ArrayList<T> inputCollection) {
        return applyFilter(inputCollection).equals(inputCollection);
    }
}
