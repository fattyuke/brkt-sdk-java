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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Static utility methods pertaining to {@link Map} instances (including instances of
 * {@link SortedMap}, {@link BiMap}, etc.). Also see this class's counterparts
 * {@link Lists}, {@link Sets} and {@link Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Maps">
 * {@code Maps}</a>.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Isaac Shum
 * @author Louis Wasserman
 * @since 2.0
 */
@GwtCompatible(emulated = true)
public final class Maps {
    private Maps() {}

    /**
     * Creates a <i>mutable</i>, empty {@code HashMap} instance.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link
     * ImmutableMap#of()} instead.
     *
     * <p><b>Note:</b> if {@code K} is an {@code enum} type, use {@link
     * #newEnumMap} instead.
     *
     * @return a new, empty {@code HashMap}
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * Creates a <i>mutable</i> {@code HashMap} instance with the same mappings as
     * the specified map.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link
     * ImmutableMap#copyOf(Map)} instead.
     *
     * <p><b>Note:</b> if {@code K} is an {@link Enum} type, use {@link
     * #newEnumMap} instead.
     *
     * @param map the mappings to be placed in the new map
     * @return a new {@code HashMap} initialized with the mappings from {@code
     *         map}
     */
    public static <K, V> HashMap<K, V> newHashMap(
            Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

    /**
     * Creates a <i>mutable</i>, empty, insertion-ordered {@code LinkedHashMap}
     * instance.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link
     * ImmutableMap#of()} instead.
     *
     * @return a new, empty {@code LinkedHashMap}
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    /**
     * Creates a <i>mutable</i>, insertion-ordered {@code LinkedHashMap} instance
     * with the same mappings as the specified map.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link
     * ImmutableMap#copyOf(Map)} instead.
     *
     * @param map the mappings to be placed in the new map
     * @return a new, {@code LinkedHashMap} initialized with the mappings from
     *         {@code map}
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(
            Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<K, V>(map);
    }

    /**
     * Creates a <i>mutable</i>, empty {@code TreeMap} instance using the natural
     * ordering of its elements.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link
     * ImmutableSortedMap#of()} instead.
     *
     * @return a new, empty {@code TreeMap}
     */
    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<K, V>();
    }

    /**
     * Creates a <i>mutable</i> {@code TreeMap} instance with the same mappings as
     * the specified map and using the same ordering as the specified map.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link
     * ImmutableSortedMap#copyOfSorted(SortedMap)} instead.
     *
     * @param map the sorted map whose mappings are to be placed in the new map
     *        and whose comparator is to be used to sort the new map
     * @return a new {@code TreeMap} initialized with the mappings from {@code
     *         map} and using the comparator of {@code map}
     */
    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return new TreeMap<K, V>(map);
    }

    /**
     * Creates a <i>mutable</i>, empty {@code TreeMap} instance using the given
     * comparator.
     *
     * <p><b>Note:</b> if mutability is not required, use {@code
     * ImmutableSortedMap.orderedBy(comparator).build()} instead.
     *
     * @param comparator the comparator to sort the keys with
     * @return a new, empty {@code TreeMap}
     */
    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(
            /* @Nullable */ Comparator<C> comparator) {
        // Ideally, the extra type parameter "C" shouldn't be necessary. It is a
        // work-around of a compiler type inference quirk that prevents the
        // following code from being compiled:
        // Comparator<Class<?>> comparator = null;
        // Map<Class<? extends Throwable>, String> map = newTreeMap(comparator);
        return new TreeMap<K, V>(comparator);
    }

    /**
     * Creates an {@code EnumMap} instance.
     *
     * @param type the key type for this map
     * @return a new, empty {@code EnumMap}
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) {
        return new EnumMap<K, V>(checkNotNull(type));
    }

    /**
     * Creates an {@code EnumMap} with the same mappings as the specified map.
     *
     * @param map the map from which to initialize this {@code EnumMap}
     * @return a new {@code EnumMap} initialized with the mappings from {@code
     *         map}
     * @throws IllegalArgumentException if {@code m} is not an {@code EnumMap}
     *         instance and contains no mappings
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(
            Map<K, ? extends V> map) {
        return new EnumMap<K, V>(map);
    }

    /**
     * Creates an {@code IdentityHashMap} instance.
     *
     * @return a new, empty {@code IdentityHashMap}
     */
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<K, V>();
    }
}
