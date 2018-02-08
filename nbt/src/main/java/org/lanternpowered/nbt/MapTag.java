/*
 * This file is part of LanternNBT, licensed under the MIT License (MIT).
 *
 * Copyright (c) LanternPowered https://github.com/LanternPowered/LanternNBT
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the Software), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andor sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lanternpowered.nbt;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

/**
 * A map tag, which can be used to map a {@link Tag} type
 * to another. This tag cannot be confused with the
 * {@link CompoundTag}, this only supports one key
 * {@link Tag} type and value {@link Tag} type.
 *
 * @param <K> The key tag type
 * @param <V> The value tag type
 */
public final class MapTag<K extends Tag<?>, V extends Tag<?>> extends HashMap<K, V> implements Tag<Map<K, V>> {

    public static Map<IntTag, DoubleTag> ofIntToDoubleMap(Map<Integer, Double> intToDoubleMap,
            @Nullable Double defaultValue) {
        return of(intToDoubleMap, IntTag::new, DoubleTag::new, defaultValue);
    }

    public static Map<IntTag, IntTag> ofIntToIntMap(Map<Integer, Integer> intToIntMap,
            @Nullable Integer defaultValue) {
        return of(intToIntMap, IntTag::new, IntTag::new, defaultValue);
    }

    public static <K extends Tag<KE>, V extends Tag<VE>, KE, VE> MapTag<K, V> of(Map<KE, VE> map,
            Function<KE, K> keyTransformer,
            Function<VE, V> valueTransformer) {
        return of(map, keyTransformer, valueTransformer, null);
    }

    public static <K extends Tag<KE>, V extends Tag<VE>, KE, VE> MapTag<K, V> of(Map<KE, VE> map,
            Function<KE, K> keyTransformer,
            Function<VE, V> valueTransformer,
            @Nullable VE defaultValue) {
        final MapTag<K, V> mapTag = new MapTag<>();
        for (Map.Entry<KE, VE> entry : map.entrySet()) {
            VE value = entry.getValue();
            if (value == null) {
                value = defaultValue;
                if (value == null) {
                    continue;
                }
            }
            final K newKey = keyTransformer.apply(entry.getKey());
            final V newValue = valueTransformer.apply(value);
            mapTag.put(newKey, newValue);
        }
        return mapTag;
    }

    /**
     * Unwraps all the {@link Tag} keys and values in the
     * {@link MapTag} and puts them into a {@link Map}.
     *
     * @param mapTag The map tag
     * @return The map
     */
    public static <K, V> Map<K, V> toMap(MapTag<? extends Tag<K>, ? extends Tag<V>> mapTag) {
        final Map<K, V> map = new HashMap<>();
        for (Map.Entry<? extends Tag<K>, ? extends Tag<V>> entry : mapTag.entrySet()) {
            map.put(entry.getKey().get(), entry.getValue().get());
        }
        return map;
    }

    /**
     * Constructs a new {@link MapTag} from
     * the given {@link Map}.
     *
     * @param map The map
     * @return The map tag
     */
    public static <K extends Tag<?>, V extends Tag<?>> MapTag<K, V> of(Map<K, V> map) {
        return new MapTag<>(map);
    }

    /**
     * Constructs a new {@link MapTag}.
     */
    public MapTag() {
    }

    private MapTag(Map<K, V> map) {
        putAll(map);
    }

    @Override
    public Map<K, V> get() {
        return this;
    }

    @Override
    public void set(Map<K, V> value) {
        clear();
        putAll(value);
    }

    @Override
    public V put(K key, V value) {
        requireNonNull(key, "A null key isn't supported");
        requireNonNull(value, "A null value isn't supported");
        return super.put(key, value);
    }
}
