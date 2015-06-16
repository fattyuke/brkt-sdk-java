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
 *
 * (this file has been modified by Bracket Computing, Inc.)
 */

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * A {@link Set} whose contents will never change, with many other important properties detailed at
 * {@link ImmutableCollection}.
 *
 * @since 2.0
 */
@GwtCompatible(serializable = true, emulated = true)
@SuppressWarnings("serial") // we're overriding default serialization
public abstract class ImmutableSet<E>
        implements Set<E> {

    /**
     * Returns an immutable set containing {@code element}. Preferred over {@link
     * Collections#singleton} for code consistency, {@code null} rejection, and because the return
     * type conveys the immutability guarantee.
     */
    public static <E> Set<E> of(E element) {
        return construct(1, element);
    }

    /**
     * Returns an immutable set containing the given elements, minus duplicates, in the order each was
     * first specified. That is, if multiple elements are {@linkplain Object#equals equal}, all except
     * the first are ignored.
     */
    public static <E> Set<E> of(E e1, E e2) {
        return construct(2, e1, e2);
    }

    /**
     * Returns an immutable set containing the given elements, minus duplicates, in the order each was
     * first specified. That is, if multiple elements are {@linkplain Object#equals equal}, all except
     * the first are ignored.
     */
    public static <E> Set<E> of(E e1, E e2, E e3) {
        return construct(3, e1, e2, e3);
    }

    /**
     * Returns an immutable set containing the given elements, minus duplicates, in the order each was
     * first specified. That is, if multiple elements are {@linkplain Object#equals equal}, all except
     * the first are ignored.
     */
    public static <E> Set<E> of(E e1, E e2, E e3, E e4) {
        return construct(4, e1, e2, e3, e4);
    }

    /**
     * Returns an immutable set containing the given elements, minus duplicates, in the order each was
     * first specified. That is, if multiple elements are {@linkplain Object#equals equal}, all except
     * the first are ignored.
     */
    public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5) {
        return construct(5, e1, e2, e3, e4, e5);
    }

    /**
     * Returns an immutable set containing the given elements, minus duplicates, in the order each was
     * first specified. That is, if multiple elements are {@linkplain Object#equals equal}, all except
     * the first are ignored.
     *
     * @since 3.0 (source-compatible since 2.0)
     */
    public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5, E e6,
                                         E... others) {
        final int paramCount = 6;
        Object[] elements = new Object[paramCount + others.length];
        elements[0] = e1;
        elements[1] = e2;
        elements[2] = e3;
        elements[3] = e4;
        elements[4] = e5;
        elements[5] = e6;
        System.arraycopy(others, 0, elements, paramCount, others.length);
        return construct(elements.length, elements);
    }

    /**
     * Constructs an {@code ImmutableSet} from the first {@code n} elements of the specified array.
     * If {@code k} is the size of the returned {@code ImmutableSet}, then the unique elements of
     * {@code elements} will be in the first {@code k} positions, and {@code elements[i] == null} for
     * {@code k <= i < n}.
     *
     * <p>This may modify {@code elements}.  Additionally, if {@code n == elements.length} and
     * {@code elements} contains no duplicates, {@code elements} may be used without copying in the
     * returned {@code ImmutableSet}, in which case it may no longer be modified.
     *
     * <p>{@code elements} may contain only values of type {@code E}.
     *
     * @throws NullPointerException if any of the first {@code n} elements of {@code elements} is
     *          null
     */
    private static <E> Set<E> construct(int n, Object... elements) {
        Set<E> set = Sets.newHashSet();
        for (Object e : elements) {
            set.add((E) e);
        }
        return Collections.unmodifiableSet(set);
    }

    /**
     * Returns an immutable set containing each of {@code elements}, minus duplicates, in the order
     * each appears first in the source collection.
     *
     * <p><b>Performance note:</b> In certain cases when {@code elements} is an {@link
     * ImmutableCollection}, its data will be reused directly without copying; the {@code elements}
     * instance itself might even be returned. The specific circumstances in which these optimizations
     * happen are undefined and subject to change.
     *
     * @throws NullPointerException if any of {@code elements} is null
     * @since 7.0 (source-compatible since 2.0)
     */
    public static <E> Set<E> copyOf(Collection<? extends E> elements) {
        Set<E> set = Sets.newHashSet();
        for (Object e : elements) {
            set.add((E) e);
        }
        return Collections.unmodifiableSet(set);
    }

    /**
     * Returns an immutable set containing each of {@code elements}, minus duplicates, in the order
     * each appears first in the source iterable. This method iterates over {@code elements} only
     * once.
     *
     * <p><b>Performance note:</b> In certain cases when {@code elements} is an {@link
     * ImmutableCollection}, its data will be reused directly without copying; the {@code elements}
     * instance itself might even be returned. The specific circumstances in which these optimizations
     * happen are undefined and subject to change.
     *
     * @throws NullPointerException if any of {@code elements} is null
     */
    public static <E> Set<E> copyOf(Iterable<? extends E> elements) {
        return copyOf(elements.iterator());
    }

    /**
     * Returns an immutable set containing each of {@code elements}, minus duplicates, in the order
     * each appears first in the source iterator.
     *
     * @throws NullPointerException if any of {@code elements} is null
     */
    public static <E> Set<E> copyOf(Iterator<? extends E> elements) {
        // We special-case for 0 or 1 elements, but anything further is madness.
        if (!elements.hasNext()) {
            return Collections.emptySet();
        }
        Set<E> set = Sets.newHashSet();
        while (elements.hasNext()) {
            set.add(elements.next());
        }
        return Collections.unmodifiableSet(set);
    }
}
