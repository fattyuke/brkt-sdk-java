/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class contains static utility methods that operate on or return objects
 * of type {@link Iterator}. Except as noted, each method has a corresponding
 * {@link Iterable}-based method in the {@link Iterables} class.
 *
 * <p><i>Performance notes:</i> Unless otherwise noted, all of the iterators
 * produced in this class are <i>lazy</i>, which means that they only advance
 * the backing iteration when absolutely necessary.
 *
 * <p>See the Guava User Guide section on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Iterables">
 * {@code Iterators}</a>.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @since 2.0
 */
@GwtCompatible(emulated = true)
public final class Iterators {
    private Iterators() {}

    /**
     * Returns the single element contained in {@code iterator}.
     *
     * @throws NoSuchElementException if the iterator is empty
     * @throws IllegalArgumentException if the iterator contains multiple
     *     elements.  The state of the iterator is unspecified.
     */
    public static <T> T getOnlyElement(Iterator<T> iterator) {
        T first = iterator.next();
        if (!iterator.hasNext()) {
            return first;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <" + first);
        for (int i = 0; i < 4 && iterator.hasNext(); i++) {
            sb.append(", " + iterator.next());
        }
        if (iterator.hasNext()) {
            sb.append(", ...");
        }
        sb.append('>');

        throw new IllegalArgumentException(sb.toString());
    }

    /**
     * Returns the single element contained in {@code iterator}, or {@code
     * defaultValue} if the iterator is empty.
     *
     * @throws IllegalArgumentException if the iterator contains multiple
     *     elements.  The state of the iterator is unspecified.
     */
    /* @Nullable */
    public static <T> T getOnlyElement(Iterator<? extends T> iterator, /* @Nullable */ T defaultValue) {
        return iterator.hasNext() ? getOnlyElement(iterator) : defaultValue;
    }

    /**
     * Adds all elements in {@code iterator} to {@code collection}. The iterator
     * will be left exhausted: its {@code hasNext()} method will return
     * {@code false}.
     *
     * @return {@code true} if {@code collection} was modified as a result of this
     *         operation
     */
    public static <T> boolean addAll(
            Collection<T> addTo, Iterator<? extends T> iterator) {
        checkNotNull(addTo);
        checkNotNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }

    /**
     * Calls {@code next()} on {@code iterator}, either {@code numberToAdvance} times
     * or until {@code hasNext()} returns {@code false}, whichever comes first.
     *
     * @return the number of elements the iterator was advanced
     * @since 13.0 (since 3.0 as {@code Iterators.skip})
     */
    public static int advance(Iterator<?> iterator, int numberToAdvance) {
        checkNotNull(iterator);
        checkArgument(numberToAdvance >= 0, "numberToAdvance must be nonnegative");

        int i;
        for (i = 0; i < numberToAdvance && iterator.hasNext(); i++) {
            iterator.next();
        }
        return i;
    }

    /**
     * Creates an iterator returning the first {@code limitSize} elements of the
     * given iterator. If the original iterator does not contain that many
     * elements, the returned iterator will have the same behavior as the original
     * iterator. The returned iterator supports {@code remove()} if the original
     * iterator does.
     *
     * @param iterator the iterator to limit
     * @param limitSize the maximum number of elements in the returned iterator
     * @throws IllegalArgumentException if {@code limitSize} is negative
     * @since 3.0
     */
    public static <T> Iterator<T> limit(
            final Iterator<T> iterator, final int limitSize) {
        checkNotNull(iterator);
        checkArgument(limitSize >= 0, "limit is negative");
        return new Iterator<T>() {
            private int count;

            @Override
            public boolean hasNext() {
                return count < limitSize && iterator.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                count++;
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    /**
     * Adapts an {@code Iterator} to the {@code Enumeration} interface.
     *
     * <p>The {@code Iterable} equivalent of this method is either {@link
     * Collections#enumeration} (if you have a {@link Collection}), or
     * {@code Iterators.asEnumeration(collection.iterator())}.
     */
    public static <T> Enumeration<T> asEnumeration(final Iterator<T> iterator) {
        checkNotNull(iterator);
        return new Enumeration<T>() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }
            @Override
            public T nextElement() {
                return iterator.next();
            }
        };
    }

}
