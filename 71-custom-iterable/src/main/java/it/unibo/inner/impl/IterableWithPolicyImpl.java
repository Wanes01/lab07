package it.unibo.inner.impl;

import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final T[] elements;
    private Predicate<T> predicate;

    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, new Predicate<T>() {
            @Override
            public boolean test(T elem) {
                return true;
            }
        });
    }

    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> predicate) {
        this.elements = elements;
        this.predicate = predicate;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.predicate = filter;
    }

    @Override
    public String toString() {
        final Iterator<T> iteratedItems = this.iterator();
        String str = "[";
        while (iteratedItems.hasNext()) {
            str += iteratedItems.next() + (iteratedItems.hasNext() ? " | " : "");
        }
        str += "]";
        return str;
    }

    private class ElementsIterator implements Iterator<T> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            for (int i = this.current; i < elements.length; i++) {
                if (predicate.test(elements[i])) {
                    this.current = i;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            return elements[this.current++];
        }
    }

}
