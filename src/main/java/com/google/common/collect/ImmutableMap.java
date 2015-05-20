/*
 * Copyright (C) 2008 The Guava Authors
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

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * A {@link Map} whose contents will never change, with many other important properties detailed at
 * {@link ImmutableCollection}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained">
 * immutable collections</a>.
 *
 * @author Jesse Wilson
 * @author Kevin Bourrillion
 * @since 2.0
 */
@GwtCompatible(serializable = true, emulated = true)
@SuppressWarnings("serial") // we're overriding default serialization
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {

    /**
     * Returns an immutable map containing a single entry. This map behaves and
     * performs comparably to {@link Collections#singletonMap} but will not accept
     * a null key or value. It is preferable mainly for consistency and
     * maintainability of your code.
     */
    public static <K, V> Map<K, V> of(K k1, V v1) {
        Map<K, V> map = Maps.newHashMap();
        map.put(k1, v1);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns an immutable map containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys are provided
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        Map<K, V> map = Maps.newHashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns an immutable map containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys are provided
     */
    public static <K, V> Map<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = Maps.newHashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns an immutable map containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys are provided
     */
    public static <K, V> Map<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = Maps.newHashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return Collections.unmodifiableMap(map);
    }

}
