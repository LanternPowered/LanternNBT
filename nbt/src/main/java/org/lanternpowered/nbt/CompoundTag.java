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

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
public final class CompoundTag extends HashMap<String, Tag<?>> implements Tag<Map<String, Tag<?>>> {

    /**
     * Constructs a new {@link CompoundTag} from
     * the given {@link Map}.
     *
     * @param map The map
     * @return The compound tag
     */
    public static CompoundTag of(Map<String, Tag<?>> map) {
        return new CompoundTag(map);
    }

    /**
     * Constructs a new {@link CompoundTag}.
     */
    public CompoundTag() {
    }

    private CompoundTag(Map<String, Tag<?>> map) {
        super(map);
    }

    @Override
    public Map<String, Tag<?>> get() {
        return this;
    }

    @Override
    public void set(Map<String, Tag<?>> value) {
        clear();
        putAll(value);
    }

    @Nullable
    @Override
    public Tag<?> put(String key, Tag<?> value) {
        requireNonNull(key, "key");
        requireNonNull(value, "value");
        return super.put(key, value);
    }

    public void putValue(String key, Object value) {
        put(key, Tag.fromObject(value));
    }

    /**
     * Gets the value of a {@link Tag} for the given key, but only if
     * the target value type matches.
     *
     * @param key The key
     * @param valueType The value type
     * @param <V> The value type
     * @return The value if found, otherwise {@code null}
     */
    @Nullable
    public <V> V getValue(String key, Class<V> valueType) {
        final Tag<?> tag = get(key);
        if (tag == null) {
            return null;
        }
        final Object value = tag.get();
        return valueType.isInstance(value) ? valueType.cast(value) : null;
    }

    /**
     * Gets the value of a {@link Tag} for the given key, but only if
     * the target {@link Tag} type matches.
     *
     * @param key The key
     * @param tagType The tag type
     * @param <V> The value type
     * @return The value if found, otherwise {@code null}
     */
    @Nullable
    public <V> V getValueByTag(String key, Class<Tag<V>> tagType) {
        final Tag<?> tag = get(key);
        if (!tagType.isInstance(tag)) {
            return null;
        }
        return (V) tag.get();
    }

    /**
     * Gets the value of a {@link Tag} for the given key.
     *
     * @param key The key
     * @return The value if found, otherwise {@code null}
     */
    @Nullable
    public Object getValue(String key) {
        final Tag<?> tag = get(key);
        return tag == null ? null : tag.get();
    }

    /**
     * Sets the {@code boolean} value for the give key.
     *
     * @param key The key
     * @param value The boolean value
     */
    public void putBoolean(String key, boolean value) {
        put(key, new BooleanTag(value));
    }

    /**
     * Attempts to get an {@code boolean} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The boolean value
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Attempts to get an {@code boolean} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The boolean value
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof BooleanTag)) {
            return defaultValue;
        }
        return ((BooleanTag) tag).booleanValue();
    }

    /**
     * Attempts to get an {@code boolean} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The boolean value
     */
    @Nullable
    public Boolean getNullableBoolean(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof BooleanTag)) {
            return null;
        }
        return ((BooleanTag) tag).get();
    }

    /**
     * Sets the {@code char} value for the give key.
     *
     * @param key The key
     * @param value The char value
     */
    public void putChar(String key, char value) {
        put(key, new CharTag(value));
    }

    /**
     * Attempts to get an {@code char} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The char value
     */
    public char getChar(String key) {
        return getChar(key, (char) 0);
    }

    /**
     * Attempts to get an {@code char} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The char value
     */
    public char getChar(String key, char defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof CharTag)) {
            return defaultValue;
        }
        return ((CharTag) tag).charValue();
    }

    /**
     * Attempts to get an {@code char} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The char value
     */
    @Nullable
    public Character getNullableChar(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof CharTag)) {
            return null;
        }
        return ((CharTag) tag).get();
    }

    /**
     * Sets the {@code byte} value for the give key.
     *
     * @param key The key
     * @param value The byte value
     */
    public void putByte(String key, byte value) {
        put(key, new ByteTag(value));
    }

    /**
     * Attempts to get an {@code byte} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The byte value
     */
    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    /**
     * Attempts to get an {@code byte} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The byte value
     */
    public byte getByte(String key, byte defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return defaultValue;
        }
        return ((NumberTag<?>) tag).byteValue();
    }

    /**
     * Attempts to get an {@code byte} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The byte value
     */
    @Nullable
    public Byte getNullableByte(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return null;
        }
        return ((NumberTag<?>) tag).byteValue();
    }

    /**
     * Sets the {@code short} value for the give key.
     *
     * @param key The key
     * @param value The short value
     */
    public void putShort(String key, short value) {
        put(key, new ShortTag(value));
    }

    /**
     * Attempts to get an {@code int} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The short value
     */
    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    /**
     * Attempts to get an {@code short} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The short value
     */
    public short getShort(String key, short defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return defaultValue;
        }
        return ((NumberTag<?>) tag).shortValue();
    }

    /**
     * Attempts to get an {@code short} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The short value
     */
    @Nullable
    public Short getNullableShort(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return null;
        }
        return ((NumberTag<?>) tag).shortValue();
    }

    /**
     * Sets the {@code int} value for the give key.
     *
     * @param key The key
     * @param value The int value
     */
    public void putInt(String key, int value) {
        put(key, new IntTag(value));
    }

    /**
     * Attempts to get an {@code int} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The int value
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Attempts to get an {@code int} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The int value
     */
    public int getInt(String key, int defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return defaultValue;
        }
        return ((NumberTag<?>) tag).intValue();
    }

    /**
     * Attempts to get an {@code int} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The int value
     */
    @Nullable
    public Integer getNullableInt(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return null;
        }
        return ((NumberTag<?>) tag).intValue();
    }

    /**
     * Sets the {@code long} value for the give key.
     *
     * @param key The key
     * @param value The long value
     */
    public void putLong(String key, long value) {
        put(key, new LongTag(value));
    }

    /**
     * Attempts to get an {@code long} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The long value
     */
    public long getLong(String key) {
        return getLong(key, 0);
    }

    /**
     * Attempts to get an {@code long} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The long value
     */
    public long getLong(String key, long defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return defaultValue;
        }
        return ((NumberTag<?>) tag).longValue();
    }

    /**
     * Attempts to get an {@code long} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The long value
     */
    @Nullable
    public Long getNullableLong(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return null;
        }
        return ((NumberTag<?>) tag).longValue();
    }

    /**
     * Sets the {@code float} value for the give key.
     *
     * @param key The key
     * @param value The float value
     */
    public void putFloat(String key, float value) {
        put(key, new FloatTag(value));
    }

    /**
     * Attempts to get an {@code float} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The float value
     */
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    /**
     * Attempts to get an {@code float} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The float value
     */
    public float getFloat(String key, float defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return defaultValue;
        }
        return ((NumberTag<?>) tag).floatValue();
    }

    /**
     * Attempts to get an {@code float} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The float value
     */
    @Nullable
    public Float getNullableFloat(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return null;
        }
        return ((NumberTag<?>) tag).floatValue();
    }

    /**
     * Sets the {@code double} value for the give key.
     *
     * @param key The key
     * @param value The double value
     */
    public void putDouble(String key, double value) {
        put(key, new DoubleTag(value));
    }

    /**
     * Attempts to get an {@code double} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The double value
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
     * Attempts to get an {@code double} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @param defaultValue The default value that should
     *                     be used if the key can't be found
     * @return The double value
     */
    public double getDouble(String key, double defaultValue) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return defaultValue;
        }
        return ((NumberTag<?>) tag).doubleValue();
    }

    /**
     * Attempts to get an {@code double} value for the given
     * key. All {@link NumberTag}s are supported.
     *
     * @param key The key
     * @return The double value
     */
    @Nullable
    public Double getNullableDouble(String key) {
        final Tag<?> tag = get(key);
        if (tag == null || !(tag instanceof NumberTag)) {
            return null;
        }
        return ((NumberTag<?>) tag).doubleValue();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
