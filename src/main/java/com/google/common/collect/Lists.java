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
import com.google.common.annotations.GwtIncompatible;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Static utility methods pertaining to {@link List} instances. Also see this
 * class's counterparts {@link Sets}, {@link Maps} and {@link Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Lists">
 * {@code Lists}</a>.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Louis Wasserman
 * @since 2.0
 */
@GwtCompatible(emulated = true)
public final class Lists {
    private Lists() {}

    // ArrayList

    /**
     * Creates a <i>mutable</i>, empty {@code ArrayList} instance (for Java 6 and
     * earlier).
     *
     * <p><b>Note:</b> if mutability is not required, use {@link
     * ImmutableList#of()} instead.
     *
     * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and
     * should be treated as deprecated. Instead, use the {@code ArrayList}
     * {@linkplain ArrayList#ArrayList() constructor} directly, taking advantage
     * of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
     */
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    /**
     * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
     * elements.
     *
     * <p><b>Note:</b> essentially the only reason to use this method is when you
     * will need to add or remove elements later. Otherwise, for non-null elements
     * use {@link ImmutableList#of()} (for varargs) or {@link
     * ImmutableList#copyOf(Object[])} (for an array) instead. If any elements
     * might be null, or you need support for {@link List#set(int, Object)}, use
     * {@link Arrays#asList}.
     *
     * <p>Note that even when you do need the ability to add or remove, this method
     * provides only a tiny bit of syntactic sugar for {@code newArrayList(}{@link
     * Arrays#asList asList}{@code (...))}, or for creating an empty list then
     * calling {@link Collections#addAll}. This method is not actually very useful
     * and will likely be deprecated in the future.
     */
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(E... elements) {
        checkNotNull(elements); // for GWT
        // Avoid integer overflow when a large array is passed in
        ArrayList<E> list = new ArrayList<E>();
        Collections.addAll(list, elements);
        return list;
    }

    /**
     * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
     * elements; a very thin shortcut for creating an empty list and then calling
     * {@link Iterators#addAll}.
     *
     * <p><b>Note:</b> if mutability is not required and the elements are
     * non-null, use {@link ImmutableList#copyOf(Iterator)} instead.
     */
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        Iterators.addAll(list, elements);
        return list;
    }

    // LinkedList

    /**
     * Creates a <i>mutable</i>, empty {@code LinkedList} instance (for Java 6 and
     * earlier).
     *
     * <p><b>Note:</b> if you won't be adding any elements to the list, use {@link
     * ImmutableList#of()} instead.
     *
     * <p><b>Performance note:</b> {@link ArrayList} and {@link
     * java.util.ArrayDeque} consistently outperform {@code LinkedList} except in
     * certain rare and specific situations. Unless you have spent a lot of time
     * benchmarking your specific needs, use one of those instead.
     *
     * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and
     * should be treated as deprecated. Instead, use the {@code LinkedList}
     * {@linkplain LinkedList#LinkedList() constructor} directly, taking advantage
     * of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
     */
    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<E>();
    }

    /**
     * Creates a <i>mutable</i> {@code LinkedList} instance containing the given
     * elements; a very thin shortcut for creating an empty list then calling
     * {@link Iterables#addAll}.
     *
     * <p><b>Note:</b> if mutability is not required and the elements are
     * non-null, use {@link ImmutableList#copyOf(Iterable)} instead. (Or, change
     * {@code elements} to be a {@link FluentIterable} and call
     * {@code elements.toList()}.)
     *
     * <p><b>Performance note:</b> {@link ArrayList} and {@link
     * java.util.ArrayDeque} consistently outperform {@code LinkedList} except in
     * certain rare and specific situations. Unless you have spent a lot of time
     * benchmarking your specific needs, use one of those instead.
     *
     * <p><b>Note for Java 7 and later:</b> if {@code elements} is a {@link
     * Collection}, you don't need this method. Use the {@code LinkedList}
     * {@linkplain LinkedList#LinkedList(Collection) constructor} directly, taking
     * advantage of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
     */
    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList(
            Iterable<? extends E> elements) {
        LinkedList<E> list = newLinkedList();
        for (E e : elements) {
            list.add(e);
        }
        return list;
    }

    /**
     * Creates an empty {@code CopyOnWriteArrayList} instance.
     *
     * <p><b>Note:</b> if you need an immutable empty {@link List}, use
     * {@link Collections#emptyList} instead.
     *
     * @return a new, empty {@code CopyOnWriteArrayList}
     * @since 12.0
     */
    @GwtIncompatible("CopyOnWriteArrayList")
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<E>();
    }

    /**
     * Returns an unmodifiable list containing the specified first element and
     * backed by the specified array of additional elements. Changes to the {@code
     * rest} array will be reflected in the returned list. Unlike {@link
     * Arrays#asList}, the returned list is unmodifiable.
     *
     * <p>This is useful when a varargs method needs to use a signature such as
     * {@code (Foo firstFoo, Foo... moreFoos)}, in order to avoid overload
     * ambiguity or to enforce a minimum argument count.
     *
     * <p>The returned list is serializable and implements {@link RandomAccess}.
     *
     * @param first the first element
     * @param rest an array of additional elements, possibly empty
     * @return an unmodifiable list containing the specified elements
     */
    public static <E> List<E> asList(/* @Nullable */ E first, E[] rest) {
        return new OnePlusArrayList<E>(first, rest);
    }

    /** @see Lists#asList(Object, Object[]) */
    private static class OnePlusArrayList<E> extends AbstractList<E>
            implements Serializable, RandomAccess {
        final E first;
        final E[] rest;

        OnePlusArrayList(/* @Nullable */ E first, E[] rest) {
            this.first = first;
            this.rest = checkNotNull(rest);
        }
        @Override public int size() {
            return rest.length + 1;
        }
        @Override public E get(int index) {
            // check explicitly so the IOOBE will have the right message
            checkElementIndex(index, size());
            return (index == 0) ? first : rest[index - 1];
        }
        private static final long serialVersionUID = 0;
    }

    /**
     * Returns an unmodifiable list containing the specified first and second
     * element, and backed by the specified array of additional elements. Changes
     * to the {@code rest} array will be reflected in the returned list. Unlike
     * {@link Arrays#asList}, the returned list is unmodifiable.
     *
     * <p>This is useful when a varargs method needs to use a signature such as
     * {@code (Foo firstFoo, Foo secondFoo, Foo... moreFoos)}, in order to avoid
     * overload ambiguity or to enforce a minimum argument count.
     *
     * <p>The returned list is serializable and implements {@link RandomAccess}.
     *
     * @param first the first element
     * @param second the second element
     * @param rest an array of additional elements, possibly empty
     * @return an unmodifiable list containing the specified elements
     */
    public static <E> List<E> asList(
            /* @Nullable */ E first, /* @Nullable */ E second, E[] rest) {
        return new TwoPlusArrayList<E>(first, second, rest);
    }

    /** @see Lists#asList(Object, Object, Object[]) */
    private static class TwoPlusArrayList<E> extends AbstractList<E>
            implements Serializable, RandomAccess {
        final E first;
        final E second;
        final E[] rest;

        TwoPlusArrayList(/* @Nullable */ E first, /* @Nullable */ E second, E[] rest) {
            this.first = first;
            this.second = second;
            this.rest = checkNotNull(rest);
        }
        @Override public int size() {
            return rest.length + 2;
        }
        @Override public E get(int index) {
            switch (index) {
                case 0:
                    return first;
                case 1:
                    return second;
                default:
                    // check explicitly so the IOOBE will have the right message
                    checkElementIndex(index, size());
                    return rest[index - 2];
            }
        }
        private static final long serialVersionUID = 0;
    }

}
