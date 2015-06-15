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

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndexes;

import com.google.common.annotations.GwtCompatible;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

import javax.annotation.Nullable;

/**
 * A {@link List} whose contents will never change, with many other important properties detailed at
 * {@link ImmutableCollection}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained">
 * immutable collections</a>.
 *
 * @see ImmutableMap
 * @see ImmutableSet
 * @author Kevin Bourrillion
 * @since 2.0
 */
@GwtCompatible(serializable = true, emulated = true)
@SuppressWarnings("serial") // we're overriding default serialization
public abstract class ImmutableList<E> /* extends ImmutableCollection<E> */
        implements List<E>, RandomAccess {
    /**
     * Returns an immutable list containing the given elements, in order. If
     * {@code elements} is a {@link Collection}, this method behaves exactly as
     * {@link #copyOf(Collection)}; otherwise, it behaves exactly as {@code
     * copyOf(elements.iterator()}.
     *
     * @throws NullPointerException if any of {@code elements} is null
     */
    public static <E> List<E> copyOf(Iterable<? extends E> elements) {
        checkNotNull(elements); // TODO(kevinb): is this here only for GWT?
        return (elements instanceof Collection)
                ? copyOf((Collection<? extends E>) elements)
                : copyOf(elements.iterator());
    }

    /**
     * Returns an immutable list containing the given elements, in order.
     *
     * <p>Despite the method name, this method attempts to avoid actually copying
     * the data when it is safe to do so. The exact circumstances under which a
     * copy will or will not be performed are undocumented and subject to change.
     *
     * <p>Note that if {@code list} is a {@code List<String>}, then {@code
     * ImmutableList.copyOf(list)} returns an {@code ImmutableList<String>}
     * containing each of the strings in {@code list}, while
     * ImmutableList.of(list)} returns an {@code ImmutableList<List<String>>}
     * containing one element (the given list itself).
     *
     * <p>This method is safe to use even when {@code elements} is a synchronized
     * or concurrent collection that is currently being modified by another
     * thread.
     *
     * @throws NullPointerException if any of {@code elements} is null
     */
    public static <E> List<E> copyOf(Collection<? extends E> elements) {
        /*
        if (elements instanceof ImmutableCollection) {
            @SuppressWarnings("unchecked") // all supported methods are covariant
                    ImmutableList<E> list = ((ImmutableCollection<E>) elements).asList();
            return list.isPartialView()
                    ? ImmutableList.<E>asImmutableList(list.toArray())
                    : list;
        }
        return construct(elements.toArray());
        */
        List<E> newList = new ArrayList<E>(elements);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an immutable list containing the given elements, in order.
     *
     * @throws NullPointerException if any of {@code elements} is null
     */
    public static <E> List<E> copyOf(Iterator<? extends E> elements) {
        // We special-case for 0 or 1 elements, but going further is madness.
        if (!elements.hasNext()) {
            return Collections.emptyList();
        }
        List<E> newList = Lists.newArrayList();
        while (elements.hasNext()) {
            newList.add(elements.next());
        }
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an immutable list containing the given elements, in order.
     *
     * @throws NullPointerException if any of {@code elements} is null
     * @since 3.0
     */
    public static <E> List<E> copyOf(E[] elements) {
        if (elements.length == 0) {
            return Collections.emptyList();
        }
        List<E> newList = Lists.newArrayList(elements);
        return Collections.unmodifiableList(newList);
    }

}
