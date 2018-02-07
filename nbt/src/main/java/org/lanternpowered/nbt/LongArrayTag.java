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

import java.util.Arrays;

public final class LongArrayTag extends ArrayTag<long[], Long> {

    /**
     * Constructs a new {@link LongArrayTag} with the
     * given {@code long} array.
     *
     * @param value The long array
     */
    public LongArrayTag(long... value) {
        super(value);
    }

    /**
     * Constructs a new {@link LongArrayTag} with the
     * given {@link Long} array. All the {@code null}
     * entries will be replaced with {@code 0}.
     *
     * @param value The long array
     */
    public LongArrayTag(Long[] value) {
        this(value, 0);
    }

    /**
     * Constructs a new {@link LongArrayTag} with the
     * given {@link Long} array. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The long array
     * @param defaultValue The value to fill up null values
     */
    public LongArrayTag(Long[] value, long defaultValue) {
        super(convert(value, defaultValue));
    }

    private static long[] convert(Long[] boxedArray, long defaultValue) {
        final long[] array = new long[boxedArray.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = boxedArray[i] == null ? defaultValue : boxedArray[i];
        }
        return array;
    }

    @Override
    public int length() {
        return this.value.length;
    }

    @Override
    public void setAt(int index, Long value) {
        this.value[index] = value;
    }

    @Override
    public Long getAt(int index) {
        return this.value[index];
    }

    @Override
    public void insertAt(int index, Long value) {
        insertAt(index, value.longValue());
    }

    /**
     * Inserts a value at the specified index.
     *
     * @param index The index
     * @param value The value
     */
    public void insertAt(int index, long value) {
        if (index < 0 || index > this.value.length) {
            throw new IndexOutOfBoundsException();
        }
        final long[] newArray = new long[this.value.length + 1];
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index, newArray, index + 1, this.value.length - index);
        newArray[index] = value;
        this.value = newArray;
    }

    @Override
    public void removeAt(int index) {
        if (index < 0 || index > this.value.length) {
            throw new IndexOutOfBoundsException();
        }
        final long[] newArray = new long[this.value.length - 1];
        System.arraycopy(this.value, 0, newArray, 0, index);
        System.arraycopy(this.value, index + 1, newArray, index, newArray.length - index);
        this.value = newArray;
    }

    /**
     * Sets the value of this {@link Tag}. All the {@code null}
     * entries will be replaced with the default value.
     *
     * @param value The long array
     * @param defaultValue The value to fill up null values
     */
    public void set(Long[] value, long defaultValue) {
        this.value = convert(value, defaultValue);
    }

    @Override
    public void set(Long[] value) {
        set(value, 0);
    }

    @Override
    public Long[] boxedArray() {
        final Long[] array = new Long[this.value.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.value[i];
        }
        return array;
    }

    @Override
    boolean arrayEquals(long[] that) {
        return Arrays.equals(this.value, that);
    }

    @Override
    String valueToString() {
        return Arrays.toString(this.value);
    }

}
