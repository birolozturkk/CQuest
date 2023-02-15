package dev.crius.cquest;

import dev.crius.cquest.model.QuestData;
import lombok.Getter;

import java.util.*;

@Getter
public class SortedList<T> extends ArrayList<T> {
    private final Comparator<T> comparator;

    public SortedList(final Comparator<T> comparator) {
        this.comparator = comparator;
        sort(comparator);
    }

    @Override
    public boolean add(T element) {
        int index = Collections.binarySearch(this, element, comparator);
        if (index < 0) index = ~index;
        super.add(index, element);
        return true;
    }

    public Optional<T> getEntry(T entry) {
        int index = Collections.binarySearch(this, entry, comparator);
        if (index < 0) return Optional.empty();
        return Optional.of(get(index));
    }
}