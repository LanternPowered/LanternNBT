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

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface Tag<V> {

    /**
     * Gets the value of this {@link Tag}.
     *
     * @return The value
     */
    V get();

    /**
     * Sets the value of this {@link Tag}. The value may be copied
     * when being applied, depending on the implementing type.
     *
     * @param value The value
     */
    void set(V value);

    /**
     * Gets the default value for the
     * specified {@link Tag} type.
     *
     * @param tagType The tag type
     * @param <V> The value type
     * @return The value
     */
    static <V> V defaultTagValue(Class<? extends Tag<V>> tagType) {
        final Class tagType0 = tagType;
        if (tagType0 == IntTag.class) {
            return (V) (Integer) 0;
        } else if (tagType0 == ShortTag.class) {
            return (V) (Short) (short) 0;
        } else if (tagType0 == ByteTag.class) {
            return (V) (Byte) (byte) 0;
        } else if (tagType0 == DoubleTag.class) {
            return (V) (Double) 0.0;
        } else if (tagType0 == FloatTag.class) {
            return (V) (Float) 0f;
        } else if (tagType0 == LongTag.class) {
            return (V) (Long) 0L;
        } else if (tagType0 == StringTag.class) {
            return (V) "";
        } else if (tagType0 == BooleanTag.class) {
            return (V) (Boolean) false;
        } else if (tagType0 == CharTag.class) {
            return (V) (Character) (char) 0;
        } else if (tagType0 == CompoundTag.class) {
            return (V) new CompoundTag();
        } else if (tagType0 == ListTag.class) {
            return (V) new ListTag<>();
        } else if (tagType0 == IntArrayTag.class) {
            return (V) new int[0];
        } else if (tagType0 == ShortArrayTag.class) {
            return (V) new short[0];
        } else if (tagType0 == ByteArrayTag.class) {
            return (V) new byte[0];
        } else if (tagType0 == DoubleArrayTag.class) {
            return (V) new double[0];
        } else if (tagType0 == FloatArrayTag.class) {
            return (V) new float[0];
        } else if (tagType0 == LongArrayTag.class) {
            return (V) new long[0];
        } else if (tagType0 == StringArrayTag.class) {
            return (V) new String[0];
        } else if (tagType0 == BooleanArrayTag.class) {
            return (V) new boolean[0];
        } else if (tagType0 == CharArrayTag.class) {
            return (V) new char[0];
        } else if (tagType0 == CompoundArrayTag.class) {
            return (V) new CompoundTag[0];
        } else if (tagType0 == MapTag.class) {
            return (V) new MapTag<>();
        } else if (tagType0 == MapArrayTag.class) {
            return (V) new MapTag[0];
        }
        throw new IllegalStateException();
    }

    /**
     * Converts the given value into a {@link Tag}.
     *
     * @param value The value
     * @return The tag
     */
    static Tag<?> fromObject(Object value) {
        if (value instanceof Tag) {
            return (Tag<?>) value;
        } else if (value instanceof CompoundTag[]) {
            return new CompoundArrayTag((CompoundTag[]) value);
        } else if (value instanceof Boolean) {
            return new BooleanTag((Boolean) value);
        } else if (value instanceof boolean[]) {
            return new BooleanArrayTag((boolean[]) value);
        } else if (value instanceof Boolean[]) {
            return new BooleanArrayTag((Boolean[]) value);
        } else if (value instanceof Byte) {
            return new ByteTag((Byte) value);
        } else if (value instanceof byte[]) {
            return new ByteArrayTag((byte[]) value);
        } else if (value instanceof Byte[]) {
            return new ByteArrayTag((Byte[]) value);
        } else if (value instanceof Character) {
            return new CharTag((Character) value);
        } else if (value instanceof char[]) {
            return new CharArrayTag((char[]) value);
        } else if (value instanceof Character[]) {
            return new CharArrayTag((Character[]) value);
        } else if (value instanceof Double) {
            return new DoubleTag((Double) value);
        } else if (value instanceof double[]) {
            return new DoubleArrayTag((double[]) value);
        } else if (value instanceof Double[]) {
            return new DoubleArrayTag((Double[]) value);
        } else if (value instanceof Float) {
            return new FloatTag((Float) value);
        } else if (value instanceof float[]) {
            return new FloatArrayTag((float[]) value);
        } else if (value instanceof Float[]) {
            return new FloatArrayTag((Float[]) value);
        } else if (value instanceof Integer) {
            return new IntTag((Integer) value);
        } else if (value instanceof int[]) {
            return new IntArrayTag((int[]) value);
        } else if (value instanceof Integer[]) {
            return new IntArrayTag((Integer[]) value);
        } else if (value instanceof Long) {
            return new LongTag((Long) value);
        } else if (value instanceof long[]) {
            return new LongArrayTag((long[]) value);
        } else if (value instanceof Long[]) {
            return new LongArrayTag((Long[]) value);
        } else if (value instanceof Short) {
            return new ShortTag((Short) value);
        } else if (value instanceof short[]) {
            return new ShortArrayTag((short[]) value);
        } else if (value instanceof Short[]) {
            return new ShortArrayTag((Short[]) value);
        } else if (value instanceof String) {
            return new StringTag((String) value);
        } else if (value instanceof String[]) {
            return new StringArrayTag((String[]) value);
        } else if (value instanceof Map) {
            final MapTag mapTag = new MapTag();
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
                mapTag.put(fromObject(entry.getKey()), fromObject(entry.getValue()));
            }
            return mapTag;
        } else if (value instanceof MapTag[]) {
            return new MapArrayTag((MapTag[]) value);
        } else if (value instanceof Map[]) {
            final Map[] maps = (Map[]) value;
            final MapTag[] mapTags = new MapTag[maps.length];
            for (int i = 0; i < maps.length; i++) {
                mapTags[i] = (MapTag) fromObject(maps[i]);
            }
            return new MapArrayTag(mapTags);
        } else if (value instanceof List) {
            final ListTag listTag = new ListTag<>();
            for (Object object : (List<?>) value) {
                listTag.add(fromObject(object));
            }
            return listTag;
        }
        throw new IllegalStateException("Unsupported value type: " + value.getClass());
    }
}
