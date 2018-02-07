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
