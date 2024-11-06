package it.unibo.inner.impl;

import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final T[] elements;
    // private Predicate<T> predicate;

    public IterableWithPolicyImpl(final T[] elements) {
        this.elements = elements;
        // this.predicate = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIterationPolicy'");
    }

    private class ElementsIterator implements Iterator<T> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return this.current < elements.length;
        }

        @Override
        public T next() {
            return elements[this.current++];
        }
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

}
